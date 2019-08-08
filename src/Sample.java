
public class Sample {
	
	private String name;		//name that the customer uses; unique within the customer
	public String getName(){
		return name;
	}
	
	private String uniqueId;	//internal reference number; unique across all samples
	public String getUniqueId(){
		return uniqueId;
	}
	
	private Tag tag;
	public Tag getTag(){
		return tag;
	}
	public Response setTag(Tag tag){	
		if(tag.sequence != null && Tag.isValidSequence(tag.sequence) ){
			this.tag = tag;
			return new Response(true, "Tag was set successfully");
		}
		else{
			return new Response(false, "Tag sequence is invalid");
		}
	}
	
	private String customerName;	
	public String getCustomerName(){
		return customerName;
	}
	
	public Sample(String name, String customerName){
		this.name = name;
		this.customerName = customerName;
		this.tag = null;
		this.uniqueId = makeUniqueId(name, customerName);
	}
	
	private static String makeUniqueId(String name, String customer){
		return customer + "-" + name;
	}
	
	public Response moveTubes(Tube sourceTube, Tube destinationTube){	
		Response r = sourceTube.removeSample(this);

		if(!r.getSuccess()){
			return r;
		}
		
		Response r2 = destinationTube.addSample(this);

		if(!r2.getSuccess()) {
			return r2;
		}
		else{
			return new Response(true, "Successfully moved sample " + uniqueId + " from " + sourceTube.barcode + " to " + destinationTube.barcode + ".");
		}
	}
	
	public void print(){
		System.out.println("Name: " + name);
		System.out.println("Unique Id: " + uniqueId);
		System.out.println("Customer: " + customerName);
		
		String tagSequence = tag != null ? tag.sequence : null;
		System.out.println("Tag: " + tagSequence);
	}
	
}
