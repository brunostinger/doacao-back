package com.cp5a.doacao.service;

import com.cp5a.doacao.dto.organizationdto.OrgFilterDTO;
import com.cp5a.doacao.dto.organizationdto.OrgLocationDTO;
import com.cp5a.doacao.dto.organizationdto.OrganizationDTO;
import com.cp5a.doacao.dto.organizationdto.OrganizationDetailDTO;
import com.cp5a.doacao.dto.userdto.*;
import com.cp5a.doacao.exception.FieldValidationException;
import com.cp5a.doacao.model.Event;
import com.cp5a.doacao.model.OccupationArea;
import com.cp5a.doacao.model.User;
import com.cp5a.doacao.model.UserStatus;
import com.cp5a.doacao.repository.UserRepository;
import com.cp5a.doacao.security.AuthorizationFilter;
import com.cp5a.doacao.specification.UserSpecification;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;
    PasswordEncoder bCryptPasswordEncoder;
    UserSpecification userSpecification;

    public OrganizationDetailDTO getOrganizationById(Integer id){
        OrganizationDetailDTO organizationDetailDTO = new OrganizationDetailDTO();
        User user = userRepository.findByIdAndUserTypeIdAndUserStatusId(id, 2, 1);
        if(user==null)
            throw new FieldValidationException("","Usuário inválido");

        BeanUtils.copyProperties(user, organizationDetailDTO);
        return organizationDetailDTO;
    }

    public User getLoggedUser(){
        User user =  getUserById(AuthorizationFilter.getLoggedUserId());
        if(user==null)
            throw new FieldValidationException("","Usuário inválido");

        return  user;
    }

    public UserBalanceDTO getUserBalance(){
        User user = userRepository.findBalanceById(AuthorizationFilter.getLoggedUserId());
        if(user==null)
            throw new FieldValidationException("","Usuário inválido");

        if(user.getBalance()==null)
            user.setBalance(0.0);

        UserBalanceDTO userBalanceDto = new UserBalanceDTO();
        BeanUtils.copyProperties(user, userBalanceDto);

        return userBalanceDto;
    }

    public User getUserById(Integer id){
        Optional<User>  user =  userRepository.findById(id);
        if(!user.isPresent()) {
            throw new FieldValidationException("","Usuário inválido");
        }
        return user.get();
    }

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User getUserByUniqueId(String uniqueId){
        return userRepository.findByUniqueId(uniqueId);
    }

    public boolean isAlreadyRegistered(User newUser){
      User user = userRepository.findByEmail(newUser.getEmail());
      if(user!=null)
          throw new FieldValidationException("","O email informado já possui cadastro");

      user = userRepository.findByUniqueId(newUser.getUniqueId());
      if(user!=null)
          throw new FieldValidationException("","O CPF/CNPJ informado já possui cadastro");

      return false;
    }

    public User createUser(User user){
        if(user.getUserType().getId().equals(1))
            user.setUserStatus(new UserStatus(1,null));
        else
            user.setUserStatus(new UserStatus(2,null));

        isAlreadyRegistered(user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public User updateUser(EditUserDTO newUserData){
        User user  = getUserById(AuthorizationFilter.getLoggedUserId());

        if(newUserData.getPassword()!=null && !newUserData.getPassword().isBlank()){
            if(!bCryptPasswordEncoder.matches(newUserData.getPassword(), user.getPassword()))
                throw new FieldValidationException("","Senha atual inválida");

            if(!newUserData.getNewPassword().equals(newUserData.getConfirmPassword()))
                throw new FieldValidationException("","Novas senhas não coincidem");

            user.setPassword(bCryptPasswordEncoder.encode(newUserData.getNewPassword()));
        }
        if(!AuthorizationFilter.getLoggedUserRole().equals("ROLE_ORG")){
            user.setName(newUserData.getName());
            user.setPhone(newUserData.getPhone());
            user.setSite(newUserData.getSite());
            user.setZipCode(newUserData.getZipCode());
            user.setAddress(newUserData.getAddress());
            user.setAddressNumber(newUserData.getAddressNumber());
            user.setAddressComplement(newUserData.getAddressComplement());
            user.setDistrict(newUserData.getDistrict());
            user.setCity(newUserData.getCity());
            user.setState(newUserData.getState());
            user.setBio(newUserData.getBio());
            user.setProfileImage(newUserData.getProfileImage());
            user.setOccupationAreas(newUserData.getOccupationAreas());
        }

        return userRepository.save(user);
    }

    public User updateManagedUser(ManagedUserDTO newUserData){
        User user  = getUserById(newUserData.getId());

        user.setName(newUserData.getName());
        user.setPhone(newUserData.getPhone());
        user.setSite(newUserData.getSite());
        user.setZipCode(newUserData.getZipCode());
        user.setAddress(newUserData.getAddress());
        user.setAddressNumber(newUserData.getAddressNumber());
        user.setAddressComplement(newUserData.getAddressComplement());
        user.setDistrict(newUserData.getDistrict());
        user.setCity(newUserData.getCity());
        user.setState(newUserData.getState());
        user.setBio(newUserData.getBio());
        user.setProfileImage(newUserData.getProfileImage());
        user.setOccupationAreas(newUserData.getOccupationAreas());
        user.setUserStatus(newUserData.getUserStatus());

        return userRepository.save(user);
    }

    public User updateUserBalance(Double value, Integer userId){
        User user  = getUserById(userId);
        user.setBalance(user.getBalance() == null ?  Double.valueOf(value) : Double.valueOf(value)+user.getBalance());
        return userRepository.save(user);
    }



    public List<OrgLocationDTO> getOrgsLocation(){
        List<User> orgs =  userRepository.findAll(userSpecification.getOrgsLocation()).stream().collect(Collectors.toList());
        List<OrgLocationDTO> orgsDTO =  new ArrayList<>();
        orgs.forEach(org ->{
            OrgLocationDTO newDto =  new OrgLocationDTO();
            BeanUtils.copyProperties(org, newDto);
            orgsDTO.add(newDto);
        });
        return orgsDTO;
    }

    public List<OrganizationDTO> getOrganizations(Integer page, Integer qty, OrgFilterDTO filter){
        Pageable pageable = PageRequest.of(page, qty);
        List<User> orgs =  userRepository.findAll( userSpecification.getOrganizations(filter), pageable).stream().collect(Collectors.toList());
        List<OrganizationDTO> orgsDTO =  new ArrayList<>();
        orgs.forEach(org ->{
            OrganizationDTO newDto =  new OrganizationDTO();
            BeanUtils.copyProperties(org, newDto);
            orgsDTO.add(newDto);
        });

        return orgsDTO;
    }

    public List<BasicUserInfoDTO> getUsers(Integer page, Integer qty, UserFilterDTO filter){
        Pageable pageable = PageRequest.of(page, qty);
        List<User> users =  userRepository.findAll( userSpecification.getUsers(filter), pageable).stream().collect(Collectors.toList());
        List<BasicUserInfoDTO> usersDTO =  new ArrayList<>();
        users.forEach(user ->{
            BasicUserInfoDTO newDto =  new BasicUserInfoDTO();
            BeanUtils.copyProperties(user, newDto);
            usersDTO.add(newDto);
        });

        return usersDTO;
    }

    public void updateLastLoginDateFoLoggedUser(String email){
        User user  = userRepository.findByEmail(email);
        user.setLastLogin(new Date());
        userRepository.save(user);
    }
}
