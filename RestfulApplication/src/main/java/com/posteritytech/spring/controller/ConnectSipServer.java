package com.posteritytech.spring.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectSipServer {

	private static String synIpMain=new String();
	private static Socket clientsocket=new Socket();
	public static PrintWriter os=null;
	public static BufferedReader  is=null;

	public ConnectSipServer(){
		}

	public void setsynIpMain(String str){
		synIpMain=str;
		}

	public String getsynIpMain(){
		return synIpMain;
		}

	public static String sendStatement(String sqlStatement){
		try{
			clientsocket=new Socket(synIpMain,2011);
			
			//clientsocket.setSoTimeout ( 0 );
			
	                System.out.println(sqlStatement);
			
			os=new PrintWriter(new OutputStreamWriter(clientsocket.getOutputStream(),"8859_1"));
			
			is=new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
			
	                byte head[]={(byte)0x7F,(byte)0x7F,(byte)0x7F,(byte)0x7F};
	                byte tail[]={(byte)0x0D};
	                String sockHead= new String(head);
	                String sockTail= new String(tail);

	                String sockHeadSql= "0000";
	                int len=sqlStatement.length()+2;
	                sockHeadSql=sockHeadSql+len;
	                System.out.println("sockHeadSql="+sockHeadSql);
	 
			sqlStatement=sockHead+sockHead+sockHeadSql.substring(sockHeadSql.length()-4,sockHeadSql.length())+"00000000"+sqlStatement+sockTail;
			
	                System.out.println(sqlStatement);
			
			
			os.println(sqlStatement);
			os.flush();
			}catch(Exception e){
				try{
	                e.printStackTrace();
					return "-1";
				}catch(Exception ex){
					return "-1";
				}
			}
			return "0";
	}
	public static String receiveStatement(String sqlStatement){
	        char[] packageBody=new char[4096];
		String receiveString=new String();
	        int packageLen = 0;
		try{
			if(is.read(packageBody,0,20)==20)
	                {
			  receiveString=String.valueOf(packageBody);
	                }
	                
	                packageLen = Integer.parseInt(receiveString.substring(8,12));
		        System.out.println("packageLen="+packageLen);	
			if(is.read(packageBody,0,packageLen)==packageLen)
	                {
	                  receiveString=null;
			  receiveString=new String(packageBody,0,packageLen);
	                }
		        System.out.println("receivedString="+receiveString);	
			return receiveString;
		}catch(Exception e){
			return "-1";
		}
	}
	public static String closeConnection(String sqlStatement){
	        byte head[]={(byte)0x7F,(byte)0x7F,(byte)0x7F,(byte)0x7F};
	        String sockHead= new String(head);
	        String sockHeadLen= "000300000000";
		sqlStatement=sockHead+sockHead+sockHeadLen+"ACK";
		try{
		        System.out.println("sqlStatement="+sqlStatement);	
			os.println(sqlStatement);
			os.flush();

			os.close();
			is.close();
			clientsocket.close();
			return "0";
		}catch(Exception e){
			return "-1";
		}
	}
}
