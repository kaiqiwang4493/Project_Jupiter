package rpc;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import db.MySQLConnection;
import entity.Item;

/**
 * Servlet implementation class ItemHistory
 */
public class ItemHistory extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemHistory() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		MySQLConnection connection = new MySQLConnection();
		JSONObject input = RpcHelper.readJSONObject(request);
		String userId = input.getString("user_id");
		Item item = RpcHelper.parseFavoriteItem(input.getJSONObject("favorite"));
		
		connection.setFavoriteItems(userId, item);
		connection.close();
		RpcHelper.writeJsonObject(response, new JSONObject().put("result", "SUCCESS"));

	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		MySQLConnection connection = new MySQLConnection();
		JSONObject input = RpcHelper.readJSONObject(request);
		String userId = input.getString("user_id");
		Item item = RpcHelper.parseFavoriteItem(input.getJSONObject("favorite"));
		
		connection.unsetFavoriteItems(userId, item.getItemId());
		connection.close();
		RpcHelper.writeJsonObject(response, new JSONObject().put("result", "SUCCESS"));

	}

}
