package com.cp5a.doacao.controller;
import com.cp5a.doacao.dto.organizationdto.OrgFilterDTO;
import com.cp5a.doacao.dto.organizationdto.OrgLocationDTO;
import com.cp5a.doacao.dto.organizationdto.OrganizationDTO;
import com.cp5a.doacao.dto.organizationdto.OrganizationDetailDTO;
import com.cp5a.doacao.dto.userdto.*;
import com.cp5a.doacao.exception.FieldValidationException;
import com.cp5a.doacao.model.User;
import com.cp5a.doacao.service.UserService;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Api(tags = {"Endpoint '/user'"})
public class UserController {

    UserService userService;

    @ApiOperation(value = "Cria um novo usuário/instituição")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@RequestBody @Validated RegisterUserDTO registerUserDTO){
        User user = new User();
        BeanUtils.copyProperties(registerUserDTO, user);
        userService.createUser(user);
    }

    @ApiOperation(value = "Retorna as informações do usuário através do ID - Disponível para admin")
    @GetMapping("/manage/info/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserInfoDTO getUserToManage(@PathVariable Integer id){
        User user = userService.getUserById(id);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(user, userInfoDTO);
        return userInfoDTO;
    }

    @ApiOperation(value = "Retorna as informações do usuário logado")
    @GetMapping("/info")
    @ResponseStatus(HttpStatus.OK)
    public UserInfoDTO getLoggedUser(){
        User user = userService.getLoggedUser();
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(user, userInfoDTO);
        return userInfoDTO;
    }

    @ApiOperation(value = "Retorna uma lista de usuários/instituições cadastrados para serem gerenciados - Disponível para admin")
    @GetMapping("/manage/list/{page}/{qty}")
    @ResponseStatus(HttpStatus.OK)
    public List<BasicUserInfoDTO> getUsersToManage(@PathVariable Integer page, @PathVariable Integer qty,
                                                   @ApiParam(name = "filter",
                                                           value = "{\"name\":\"\",\"type\":\"\",\"status\":\"\"} \n(type: 0 = Admin type: 1 = Usuário type: 2 = Instituiçao) \n " +
                                                                   "(status: 1 = Ativo status: 2 = Pendente status: 3 = Bloqueado)", defaultValue = "")
                                                   @RequestParam  String filter){
        UserFilterDTO filterDTO = new UserFilterDTO();
        Gson gson = new Gson();
        try{
            filterDTO = gson.fromJson(filter, UserFilterDTO.class);
        }catch (JsonSyntaxException e){
            throw new FieldValidationException("","Filtro inválido");
        }
        return userService.getUsers(page, qty, filterDTO);
    }

    @ApiOperation(value = "Retorna o saldo do usuário/instituição")
    @GetMapping("/balance")
    @ResponseStatus(HttpStatus.OK)
    public UserBalanceDTO getUserBalance(){
        return userService.getUserBalance();
    }

    @ApiOperation(value = "Retorna infomações de uma instituição através do ID")
    @GetMapping("/organization/{id}")
    @ResponseStatus(HttpStatus.OK)
    public OrganizationDetailDTO getOrganizationById(@PathVariable Integer id){
        return userService.getOrganizationById(id);
    }

    @ApiOperation(value = "Retorna uma lista contendo as instituições cadastradas e ativas")
    @GetMapping("/organization/list/{page}/{qty}")
    @ResponseStatus(HttpStatus.OK)
    public List<OrganizationDTO> getOrganizations(@PathVariable Integer page, @PathVariable Integer qty,
                                                  @ApiParam(name = "filter", value = "{\"name\":\"\",\"city\":\"\",\"category\":\"\"}", defaultValue = "")
                                                  @RequestParam  String filter){

        OrgFilterDTO filterDTO = new OrgFilterDTO();
        Gson gson = new Gson();
        try{
            filterDTO = gson.fromJson(filter, OrgFilterDTO.class);
        }catch (JsonSyntaxException e){
            throw new FieldValidationException("","Filtro inválido");
        }

        return userService.getOrganizations(page, qty, filterDTO);
    }

    @ApiOperation(value = "Retorna uma lista contendo as cidades das instituições cadastradas")
    @GetMapping("/organization/list/city")
    @ResponseStatus(HttpStatus.OK)
    public List<OrgLocationDTO> getOrgsLocartion(){
        return userService.getOrgsLocation();
    }


    @ApiOperation(value = "Atualiza as informações do usuário logado")
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@RequestBody @Validated EditUserDTO userDto){
        User user = new User();
        userService.updateUser(userDto);
    }

    @ApiOperation(value = "Atualiza as informações do usuário especificado - Disponível para admin")
    @PatchMapping("/manage")
    @ResponseStatus(HttpStatus.OK)
    public void updateManagedUser(@RequestBody @Validated ManagedUserDTO managedUserDTO){
        User user = new User();
        userService.updateManagedUser(managedUserDTO);
    }

    @ApiOperation(value = "Solicita a recuperação da senha através do email")
    @PutMapping("/recover")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public void recoverPassword(@RequestBody UserInfoDTO userInfoDTO){
        //Validação do envio de email não implementada
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

}
