package sanger_application_2019.coding_task;

public class Response {
	private Boolean success;
	public Boolean getSuccess(){
		return success;
	}
	private String message;
	public String getMessage(){
		return message;
	}
	
	public Response(Boolean success, String message){
		this.success = success;
		this.message = message;
	}
}