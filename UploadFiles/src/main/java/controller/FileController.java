package controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ws.rs.core.Response;

import dao.FileDAO;
import entity.File;

public class FileController {

	private File file = null;

	public FileController() {
		super();
	}

	
/*	public File Insert(String name, InputStream fileInputStream, String fileMetaData) {
		if(!this.CheckInput(name, this.path2) || !this.CreateDirectory()) {
			return this.file;
		}
		//fazer verificaçao de extensao jpg gif png ...
		if(fileMetaData.contains(".") && fileMetaData.lastIndexOf(".") != 0) {
			if(this.CheckExtension(fileMetaData.substring(fileMetaData.lastIndexOf(".")+1).toUpperCase())) {
		//		this.file = new FileDAO().Insert(name, this.path2, fileInputStream, fileMetaData);				
			}
		}
		System.out.println(fileMetaData.substring(fileMetaData.lastIndexOf(".")+1));
		System.out.println(fileInputStream);
		
		return file;
	}*/
	public Response Insert(String name, InputStream fileInputStream, String fileMetaData) {
		FileDAO fileDAO = new FileDAO();
		
		if(!this.CheckInput(name)) {
			return Response.ok("Nome esta vazio").status(501).build();
		}
		if(!fileDAO.CreateDirectory()) {
			return Response.ok("Nao foi possivel criar o direitorio").status(501).build();
		}
		
		if(fileMetaData.contains(".") && fileMetaData.lastIndexOf(".") != 0) {
			String extensionFile = this.CheckExtensionFile(fileMetaData.substring(fileMetaData.lastIndexOf(".")+1).toUpperCase());
			
			if(extensionFile != "") {
				this.file = new FileDAO().Insert(name, extensionFile, fileInputStream, fileMetaData);	
				
				if(this.file != null) {
					return Response.ok("Inserido com sucesso").entity(this.file).build();
				}
			}
		}
		return Response.ok("Não foi possivel salvar a imagem").status(501).build();
	}
	
	
/*	public File GetFilesList() {
		return new FileDAO().GetFilesList();
	}*/
	public Response GetFilesList() {
		this.file = new FileDAO().GetFilesList();

		if(this.file != null) {
			return Response.ok("Lista cheia").entity(this.file).build();
		}else {
			return Response.ok("Nenhum conteudo disponivel").status(501).build();
		}
	}
	
	
	public Response FindFileById(int fileId) {
		if(fileId <= 0) {
			return Response.ok("Id invalido").status(501).build();
		}
		this.file = new FileDAO().FindFileById(fileId);
		if(this.file != null) {
			return Response.ok("Conteudo encontrado").entity(this.file).build();
		}else {
			return Response.ok("Nenhum conteudo encontrado").status(501).build();
		}
	}
	
	
	public Response Delete(int fileId) {
		this.file = new FileDAO().DeleteFile(fileId);
		if(this.file != null) {
			return Response.ok("Conteudo deletado").entity(this.file).build();
		}else {
			return Response.ok("Erro ao deletar o conteudo").status(501).build();
		}
	}
	
	
	public Response GetFilesListBase64() {
		this.file = new FileDAO().GetFilesListBase64();
		
		if(this.file != null) {
			return Response.ok("Lista cheia").entity(this.file).build();
		}else {
			return Response.ok("Nenhum conteudo disponivel").status(501).build();
		}
	}
	
	public Response FindFileByIdBase64(int fileId) {
		if(fileId <= 0) {
			return Response.ok("Id invalido").status(501).build();
		}
		this.file = new FileDAO().FindFileByIdBase64(fileId);
		if(this.file != null) {
			return Response.ok("Conteudo encontrado").entity(this.file).build();
		}else {
			return Response.ok("Nenhum conteudo encontrado").status(501).build();
		}
		
	}
	
	
	
	private boolean CheckInput(String name) {
		if(name == null || name.isEmpty()) {
			return false;
		}
		return true;
	}

		
	private String CheckExtensionFile(String extension) {
		String extChecked = "";
		String[] types = {"JPG", "JPEG", "PNG"};
		for(String type : types) {
			if(extension.equals(type)){
				extChecked = extension;
			}
		}
		return extChecked;
	}
}
