package com.jdbcexample.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.jdbcexample.model.Usuario;
import com.jdbcexample.service.UsuarioService;

@CrossOrigin(origins="*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController()
public class UsuarioController {

	//LEER todos
	@GetMapping("/api")
	public List<Usuario> getAll() throws ClassNotFoundException{
		return UsuarioService.obtenerTodos();
	}
	//LEER por id
	@GetMapping("/api/{id}")
	public Usuario getById(@PathVariable String id) throws ClassNotFoundException{
		Usuario user = UsuarioService.obtenerPorId(id);
		if(user != null) {
			return user;
		}
		else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
		}
	}
	// POST -> Mandar un nuevo usuario o sea CREATE
	@PostMapping("/api")
	public Usuario postUsuario(@RequestBody Usuario user) {
		return UsuarioService.insertarUsuario(user);
	}
	// PUT -> Actualizar un registro
	@PutMapping("/api")
	public Usuario putUsuario(@RequestBody Usuario user, @PathVariable String id) throws ClassNotFoundException {
		Usuario encontrado = UsuarioService.obtenerPorId(id);
		if (encontrado == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
		} else {
			return UsuarioService.actualizarUsuario(new Usuario(Integer.parseInt(id), user.getUserName(), user.getPassword()));
		}
	}
	//Delete -> Borrar un registro 
	@DeleteMapping("/api/{id}")
	public void deleteUsuario(@PathVariable String id) throws ClassNotFoundException{
		int resultado = UsuarioService.borrarUsuario(id);
		if(resultado == 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
		}
		else {
			throw new ResponseStatusException(HttpStatus.ACCEPTED, "Usuario eliminado correctamente");
		}
	}
}