package com.jdbcexample.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.jdbcexample.model.Usuario;

public class UsuarioService {

	@Value("${spring.datasource.url}")
	String urlDB;
	
	@Value("${spring.datasource.username}")
	String userDB;
	
	@Value("${spring.datasource.password}")
	String passDB;
	
	
	public static List<Usuario> obtenerTodos() throws ClassNotFoundException{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","Cc1030671497");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM USUARIOS");
			ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
			while(rs.next()) {				
				usuarios.add(new Usuario(Integer.parseInt(rs.getString(1)),rs.getString(2),rs.getString(3)));
			}
			return usuarios;
		}
		catch(Exception ex) {
			System.out.println(ex);
			return null;
		}
	}	
	
	public static Usuario obtenerPorId(String id) throws ClassNotFoundException{
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","Cc1030671497");
			Statement st = con.createStatement();
			try {
				id = String.valueOf(Integer.parseInt(id));
			}
			catch(Exception ex) {
				id = "0";
			}
			ResultSet rs = st.executeQuery("SELECT * FROM USUARIOS WHERE ID = "+id.trim());
			if(rs.next()) {				
				return new Usuario(Integer.parseInt(rs.getString(1)),rs.getString(2),rs.getString(3));
			}	
			else {
				return null;
			}
		}catch(Exception ex) {
			System.out.println(ex);
			return null;
		}
	}
	
	public static Usuario insertarUsuario(Usuario user) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system",
					"Cc1030671497");
			Statement st = con.createStatement();
			st.executeUpdate("insert into USUARIOS (ID, USUARIO, PASSWORD) values ((select max(ID)+1 from USUARIOS),'"+user.getUserName()+"','"+user.getPassword()+"')");
			ResultSet rs = st.executeQuery("SELECT * FROM USUARIOS WHERE ID = (SELECT max(ID) FROM USUARIOS)");
			if(rs.next()) {
				return new Usuario(Integer.parseInt(rs.getString(1)),rs.getString(2),rs.getString(3));
			}
			else {
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex);
			return null;
		}
	}
	
	public static Usuario actualizarUsuario(Usuario user) {
		try {			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system",
					"Cc1030671497");
			Statement st = con.createStatement();			
			st.executeUpdate("UPDATE USUARIOS SET USUARIO='"+user.getUserName()+"', PASSWORD='"+user.getPassword()+"' WHERE ID = "+user.getId());
			ResultSet rs = st.executeQuery("SELECT * FROM USUARIOS WHERE ID = "+user.getId()+"");
			
			if(rs.next()) {
				return new Usuario(Integer.parseInt(rs.getString(1)),rs.getString(2),rs.getString(3));
			}
			else {
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex);
			return null;
		}
	}
	
	public static int borrarUsuario(String id) {
		try {			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system",
					"Cc1030671497");
			Statement st = con.createStatement();			
			return st.executeUpdate("DELETE FROM USUARIOS WHERE ID = "+id);
		} catch (Exception ex) {
			System.out.println(ex);
			return 0;
		}
	}
}
