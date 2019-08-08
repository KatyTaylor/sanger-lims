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