package com.augustojaml.appposts.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import com.augustojaml.appposts.dtos.RoleDTO;
import com.augustojaml.appposts.models.RoleModel;
import com.augustojaml.appposts.services.RolesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(value = "/roles")
public class RolesResource {

  @Autowired
  RolesService rolesService;

  @PreAuthorize("hasAnyRole('ADMIN')")
  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<RoleDTO> findById(@PathVariable String id) {
    RoleModel roleModel = this.rolesService.findById(id);
    RoleDTO roleDTO = new RoleDTO(roleModel);
    return ResponseEntity.ok().body(roleDTO);
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @RequestMapping(method = RequestMethod.GET)
  public ResponseEntity<List<RoleDTO>> findAll() {
    List<RoleModel> rolesModel = this.rolesService.findAll();
    List<RoleDTO> roleDTOs = this.rolesService.toDTO(rolesModel);
    return ResponseEntity.ok().body(roleDTOs);
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<Void> save(@Valid @RequestBody RoleDTO roleDTO) {
    RoleModel roleModel = this.rolesService.save(this.rolesService.fromDTO(roleDTO));
    URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(roleModel.getId()).toUri();
    return ResponseEntity.created(uri).build();
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Void> delete(@PathVariable String id) {
    this.rolesService.delete(id);
    return ResponseEntity.noContent().build();
  }

  @PreAuthorize("hasAnyRole('ADMIN')")
  @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
  public ResponseEntity<RoleDTO> update(@PathVariable String id, @Valid @RequestBody RoleDTO jsonRole) {
    RoleModel roleModel = this.rolesService.update(id, this.rolesService.fromDTO(jsonRole));
    RoleDTO roleDTO = new RoleDTO(roleModel);
    return ResponseEntity.ok().body(roleDTO);
  }

}
