package entity;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

/*
 * Key points of Builder pattern
 * 1. Item constructor must be private the.
 * 2. ItemBuilder must be inner static class.
 * 
 */

public class Item {
	//to save the informations get from GitHub API
	private String itemId;
	private String name;
	private String address;
	//the item has same priority, or you can use Map to store different priority.
	private Set<String> keywords;
	private String imageUrl;
	private String url;
	
	
	/*
	 * ItemBuilder builder = new ItemBuilder();
	 * builder.setName = "abc"
	 * *****
	 * Item item = new Item(builder);
	 */
	
	private Item(ItemBuilder builder) {
		this.itemId = builder.itemId;
		this.name = builder.name;
		this.address = builder.address;
		this.imageUrl = builder.imageUrl;
		this.url = builder.url;
		this.keywords = builder.keywords;
	}

	
	public String getItemId() {
		return itemId;
	}
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public Set<String> getKeywords() {
		return keywords;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public String getUrl() {
		return url;
	}

	//transfer JAVA item to JSONObject
	// return JSONObject to let the method can put multiple variables.
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		obj.put("item_id", itemId);
		obj.put("name", name);
		obj.put("address", address);
		obj.put("keywords", new JSONArray(keywords));
		obj.put("image_url", imageUrl);
		obj.put("url", url);
		return obj;
	}
	
	//builder pattern
	
	// must use static inner class
	// if the ItemBuilder is not static, then ItemBuilder must be instanced before instance Item. 
	// But we want to use Builder to create Item.
	public static class ItemBuilder {
		//ItemBuilder has the local variable same as Item's variable
		private String itemId;
		private String name;
		private String address;
		private String imageUrl;
		private String url;
		private Set<String> keywords;
		
		public void setItemId(String itemId) {
			this.itemId = itemId;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public void setImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public void setKeywords(Set<String> keywords) {
			this.keywords = keywords;
		}
		
		public Item build() {
			return new Item(this);
			//new an Item object that has the content are set by ItemBuilder.
			//this is the ItemBuilder's instance includes the information are set by ItemBuilder's setMethod
		}
		
	}
	
	

}
