package entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

@XmlRootElement
public class File {
	@XmlAttribute(required = false)
	private Integer id;
	private String name;
	private String path;
	@XmlAttribute(required = false)
	private LocalDateTime datetime;
	@XmlAttribute(required = false)
	private String datetimeString;
	@XmlAttribute(required = false)
	private List<File> files;

	public File() {
	}

	public File(Integer id, String name, String path, LocalDateTime datetime) {
		super();
		this.id = id;
		this.name = name;
		this.path = path;
		this.datetime = datetime;
	}
	
	

	public File(Integer id, String name, String path, LocalDateTime datetime, String datetimeString, List<File> files) {
		super();
		this.id = id;
		this.name = name;
		this.path = path;
		this.datetime = datetime;
		this.datetimeString = datetimeString;
		this.files = files;
	}

	public File(String name, String path) {
		super();
		this.name = name;
		this.path = path;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(Timestamp timestamp) {
		this.datetime = timestamp.toLocalDateTime();
	}

	public String getDatetimeString() {
		return  datetimeString;
	}

	public void setDatetimeString(Timestamp timestamp) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		this.datetimeString = sdf.format(timestamp);
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}
	
}
