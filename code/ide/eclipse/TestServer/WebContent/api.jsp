<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%

out.clear();

String action = request.getParameter("action");

if(action!=null){
	if(action.equals("send_pass")){
		out.print("{\"status\":1}");
	}else if(action.equals("login")){
		out.print("{\"status\":1,\"token\":\"asdasdasdsadasd\"}");
	}else if(action.equals("upload_contacts")){
		out.print("{\"status\":1}");
	}else if(action.equals("timeline")){
		out.print("{\"status\":1,\"page\":1,\"perpage\":20,\"timeline\":["+
	"{\"msg\":\"Haha1\",\"phone_md5\":\"dasdasdbkas\",\"msgId\":\"1231234\"},"+
	"{\"msg\":\"Haha2\",\"phone_md5\":\"dasdasdbkas\",\"msgId\":\"1231234\"},"+
	"{\"msg\":\"Haha3\",\"phone_md5\":\"dasdasdbkas\",\"msgId\":\"1231234\"},"+
	"{\"msg\":\"Haha4\",\"phone_md5\":\"dasdasdbkas\",\"msgId\":\"1231234\"},"+
	"{\"msg\":\"Haha5\",\"phone_md5\":\"dasdasdbkas\",\"msgId\":\"1231234\"},"+
	"{\"msg\":\"Haha6\",\"phone_md5\":\"dasdasdbkas\",\"msgId\":\"1231234\"},"+
	"{\"msg\":\"Haha7\",\"phone_md5\":\"dasdasdbkas\",\"msgId\":\"1231234\"},"+
	"{\"msg\":\"Haha8\",\"phone_md5\":\"dasdasdbkas\",\"msgId\":\"1231234\"}"+
				"]}");
	}else if(action.equals("get_comment")){
		out.print("{\"status\":1,\"page\":1,\"perpage\":20,\"msgId\":\"12133\",\"comments\":["+
	"{\"content\":\"Hehe1\",\"phone_md5\":\"dasdasdbkas\"},"+
	"{\"content\":\"Hehe2\",\"phone_md5\":\"dasdasdbkas\"},"+
	"{\"content\":\"Hehe3\",\"phone_md5\":\"dasdasdbkas\"},"+
	"{\"content\":\"Hehe4\",\"phone_md5\":\"dasdasdbkas\"},"+
	"{\"content\":\"Hehe5\",\"phone_md5\":\"dasdasdbkas\"},"+
	"{\"content\":\"Hehe6\",\"phone_md5\":\"dasdasdbkas\"},"+
	"{\"content\":\"Hehe7\",\"phone_md5\":\"dasdasdbkas\"},"+
	"{\"content\":\"Hehe8\",\"phone_md5\":\"dasdasdbkas\"}"+
				"]}");
	}else if(action.equals("pub_comment")){
		out.print("{\"status\":1}");
	}else if(action.equals("publish")){
		out.print("{\"status\":1}");
	}
}else{
	out.print("请指定action");
}

%>