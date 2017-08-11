package com.tiamaes.bike.common.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EventListener;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.NullArgumentException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * FTP客户端工具类
 * @author chen
 * @date 2013-03-03
 * @version 1.0
 */
public class FTPClientUtils {
	private static Logger logger = LogManager.getLogger(FTPClientUtils.class);
	
	public interface TransforEventListener extends EventListener{
		void update(long send,long size ) ;
	}
	
	private static final int DEFAULT_BUFFER_SIZE = 10240;
	
	/**
	 * 连接FTP服务器
	 * @param server Format is ip:port.If the port number of the default values ​​can be ignored fill
	 * @param username
	 * @param password
	 * @return
	 * @throws IOException
	 */
	public static final FTPClient connectServer(String server,String username,String password) throws IOException{
		FTPClient ftp = new FTPClient();
		try {
			int reply;
			if(StringUtils.isNotBlank(server)){
				String[] parts = server.split(":");
				String hostname = null;
				int port = 0;
				if(parts.length == 2){
					hostname = parts[0];
					port = Integer.parseInt(parts[1]);
				}else{
					hostname = server;
					port = ftp.getDefaultPort();
				}
				if(port > 0){
					ftp.connect(hostname, port);
				}else{
					ftp.connect(hostname);
				}
				logger.debug("Connetct to " + hostname + " on " + port);
			}else{
				throw new NullArgumentException("Parameters 'server' can not be empty!");
			}
			if(!ftp.login(username, password)){
				ftp.logout();
				throw new IOException("Invalid user name or password!");
			}
			logger.debug("Remote system is " + ftp.getSystemType());
			reply = ftp.getReplyCode();
			if(!FTPReply.isPositiveCompletion(reply)){
				ftp.disconnect();
				throw new IOException("FTP server refused connection!");
	        }
			// Use passive mode as default because most of us are behind firewalls these days.
//			ftp.enterLocalPassiveMode();
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.setBufferSize(DEFAULT_BUFFER_SIZE);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			throw new IOException("Unable to connect to remote server!");
		}
		return ftp;
	}

	/**
	 * 上传文件到远程FTP服务器
	 * <p>
	 * 	<code>
	 * 		File file = new File("C:\\Users\\Chen\\Desktop\\MV2.0.xlsx");
	 *		FTPClientUtils.upload("172.16.3.71:2122", "admin", "admin", new String("MV2.1.xlsx".getBytes(),"ISO-8859-1"), file);
	 *  </code>
	 * </p>
	 * @param hostname	远程服务地址:端口(端口默认为21)
	 * @param username	登录用户名称
	 * @param password	登录用户密码
	 * @param filename	指定远程文件名称
	 * @param local		要上传的本地文件
	 * @throws IOException
	 */
	public static void upload(String hostname, String username, String password, String filename, File local) throws IOException{
		upload(hostname, username, password, null, filename, local, null);
	}

	/**
	 * 上传文件到远程FTP服务器
	 * 
	 * <p>
	 * 	<code>
	 * 		File file = new File("C:\\Users\\Chen\\Desktop\\MV2.0.xlsx");
	 *		FTPClientUtils.upload("172.16.3.71:2122", "admin", "admin", "/", "MV2.1.xlsx", new FileInputStream(file));
	 *  </code>
	 * </p>
	 * @param hostname	远程服务地址:端口(端口默认为21)
	 * @param username	登录用户名称
	 * @param password	登录用户密码
	 * @param directory 指定远程目录
	 * @param filename	指定远程文件名称
	 * @param local		要上传的本地文件
	 * @throws IOException
	 */
	public static void upload(String hostname,String username,String password,String directory,String filename,File local) throws IOException{
		upload(hostname, username, password, directory, filename, local, null);
	}
	
	/**
	 * 上传文件
	 * <p>
	 * 	<code>
	 * 		File file = new File("C:\\Users\\Chen\\Desktop\\MV2.0.xlsx");
	 *		FTPClientUtils.upload("172.16.3.71:2122", "admin", "admin", "/","MV2.1.xlsx", new FileInputStream(file));</br>
	 *  </code>
	 * </p>
	 * @param hostname
	 * @param port
	 * @param username
	 * @param password
	 * @param path
	 * @param filename
	 * @param input
	 * @return
	 * @throws IOException 
	 */
	public static void upload(String hostname,String username,String password,String path,String filename,InputStream input) throws IOException{
		FTPClient ftp = connectServer(hostname,username,password);
		try {
			if(changeWorkingDirectory(ftp, path)){
				ftp.storeFile(path + filename, new BufferedInputStream(input));
			}
	        logger.debug("The file has been uploaded successfully!");
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			throw new IOException("Failed to upload files!");
		}finally{
			if(input != null){
				input.close();	
			}
			if(ftp != null && ftp.isConnected()){
				ftp.logout();
				ftp.disconnect();
			}
		}
	}

	/**
	 * 上传文件到远程FTP服务器
	 * <p>
	 * 	<code>
	 * 		File file = new File("C:\\Users\\Chen\\Desktop\\MV2.0.xlsx");
	 *		FTPClientUtils.upload("211.142.200.18:2121", "admin", "admin", "359E9A79C99640EDAD14B8C704D927D4.mkv", file, new TransforEventListener(){
	 *			@Override
	 *			public void update(long send, long size) {
	 *				System.out.println(send);
	 *				System.out.println(size);
	 *			}
	 *		});
	 *  </code>
	 * </p>
	 * @param hostname	远程服务地址:端口(端口默认为21)
	 * @param username	登录用户名称
	 * @param password	登录用户密码
	 * @param filename	指定远程文件名称
	 * @param local		要上传的本地文件
	 * @param listener	传输进度监听
	 * @throws IOException
	 */
	public static void upload(String hostname,String username,String password,String filename,File local, TransforEventListener listener) throws IOException{
		upload(hostname, username, password, null, filename, local, listener);
	}
	/**
	 * 上传文件到远程FTP服务器
	 * 
	 * <p>
	 * 	<code>
	 * 		File file = new File("C:\\Users\\Chen\\Desktop\\MV2.0.xlsx");
	 *		FTPClientUtils.upload("211.142.200.18:2121", "admin", "admin", "359E9A79C99640EDAD14B8C704D927D4.mkv", file, new TransforEventListener(){
	 *			@Override
	 *			public void update(long send, long size) {
	 *				System.out.println(send);
	 *				System.out.println(size);
	 *			}
	 *		});
	 *  </code>
	 * </p>
	 * @param hostname	远程服务地址:端口(端口默认为21)
	 * @param username	登录用户名称
	 * @param password	登录用户密码
	 * @param directory 指定远程目录
	 * @param filename	指定远程文件名称
	 * @param local		要上传的本地文件
	 * @param listener	传输进度监听
	 * @throws IOException
	 */
	public static void upload(String hostname,String username,String password,String directory,String filename,File local, TransforEventListener listener) throws IOException{
		FTPClient ftp = connectServer(hostname,username,password);
		int n = -1;
		long size = local.length();
		long trans = 0;
		int bufferSize = ftp.getBufferSize();
		byte[] buffer = new byte[bufferSize];
		
		try {
			if(directory != null){
				changeWorkingDirectory(ftp, directory);
			}
			
			RandomAccessFile raf = new RandomAccessFile(local,"r");
			FTPFile[] remotes = ftp.listFiles(filename);
			if(remotes.length == 1){
				trans = remotes[0].getSize();
			}
			if(trans > 0){
				ftp.setRestartOffset(trans);
				raf.seek(trans);
			}
			OutputStream outputstream = ftp.appendFileStream(filename);
			while((n = raf.read(buffer)) != -1){
				outputstream.write(buffer, 0, n);
				trans += n;
				if(listener != null){
					listener.update(trans, size);
				}
			}
			
			outputstream.flush();
			raf.close();
			outputstream.close();
            logger.debug("The file has been uploaded successfully!");
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
			throw new IOException("Failed to upload files!");
		}finally{
			if(ftp != null && ftp.isConnected()){
				ftp.logout();
				ftp.disconnect();
			}
		}
	}
	
	
	/**
	 * 删除文件
	 * @param hostname
	 * @param username
	 * @param password
	 * @param filename
	 */
	public static void deleteFile(String hostname,String username,String password,String filename) throws IOException{
		FTPClient ftp = connectServer(hostname,username,password);
		try {
			ftp.deleteFile(filename);
            logger.debug(String.format("The file [%s] has been deleted successfully!", filename));
		}finally{
			if(ftp != null && ftp.isConnected()){
				ftp.logout();
				ftp.disconnect();
			}
		}
	}
	/**
	 * 删除文件夹
	 * @param hostname
	 * @param username
	 * @param password
	 * @param pathname
	 * @throws IOException
	 */
	public static void deleteDirectory(String hostname, String username, String password, String pathname) throws IOException{
		FTPClient ftp = connectServer(hostname,username,password);
		try {
			if(ftp.changeWorkingDirectory(pathname)){
				_deleteDirectory(ftp, pathname);
				ftp.removeDirectory(pathname);
			}
            logger.debug(String.format("The directory [%s] has been deleted successfully!", pathname));
		}finally{
			if(ftp != null && ftp.isConnected()){
				ftp.logout();
				ftp.disconnect();
			}
		}
	}
	
	private static void _deleteDirectory(FTPClient ftp, String directory) throws IOException{
		if(ftp.changeWorkingDirectory(directory)){
			FTPFile[] files = ftp.listFiles(directory);
			for(FTPFile file : files){
				if(file.isDirectory()){
					String dir = directory + file.getName() + "/";
					_deleteDirectory(ftp, dir);
					ftp.removeDirectory(dir);
				}
				if(file.isFile()){
					ftp.deleteFile(directory + file.getName());
				}
			}
		}
	}
	/**
	 * 下载文件到本地
	 * @param hostname	远程服务地址:端口(端口默认为21)
	 * @param username	登录用户名称
	 * @param password	登录用户密码
	 * @param remote	指定远程文件名称
	 * @param local		保存的本地地址，包括文件名
	 * @throws IOException
	 */
	public static void download(String hostname, String username, String password, String remote, String local) throws IOException{
		download(hostname, username, password, null, remote, local, null);
	}
	/**
	 * 下载文件到本地
	 * @param hostname	远程服务地址:端口(端口默认为21)
	 * @param username	登录用户名称
	 * @param password	登录用户密码
	 * @param directory 指定远程目录
	 * @param remote	指定远程文件名称
	 * @param local		保存的本地地址，包括文件名
	 * @throws IOException
	 */
	public static void download(String hostname, String username, String password, String directory, String remote, String local) throws IOException{
		download(hostname, username, password, directory, remote, local, null);
	}
	/**
	 * 下载文件到本地
	 *	String local = "F:\\download\\Saikou.no.Rikon.Ep01.Chi_Jap.HDTVrip.1024X576-YYeTs.mkv";
	 *	FTPClientUtils.download("211.142.200.18:2121", "admin", "admin", "359E9A79C99640EDAD14B8C704D927D4.mkv", local, new TransforEventListener(){
	 *		@Override
	 *		public void update(long send, long size) {
	 *			System.out.println((int)Math.floor((double) send / size * 100) + "%");
	 *		}
	 *	});
	 * @param hostname	远程服务地址:端口(端口默认为21)
	 * @param username	登录用户名称
	 * @param password	登录用户密码
	 * @param remote	指定远程文件名称
	 * @param local		保存的本地地址，包括文件名
	 * @param listener  传输进度监听器
	 * @throws IOException
	 */
	public static void download(String hostname, String username, String password, String remote, String local, TransforEventListener listener) throws IOException{
		download(hostname, username, password, null, remote, local, listener);
	}
	
	/**
	 * 下载文件到本地
	 * @param hostname	远程服务地址:端口(端口默认为21)
	 * @param username	登录用户名称
	 * @param password	登录用户密码
	 * @param directory 指定远程目录
	 * @param remote	指定远程文件名称
	 * @param local		保存的本地地址，包括文件名
	 * @param listener	传输进度监听器
	 * @throws IOException
	 */
	public static void download(String hostname, String username, String password, String directory, String remote, String local, TransforEventListener listener) throws IOException{
		File localFile = new File(local);
		
		FTPClient ftp = connectServer(hostname,username,password);
		try {
			if(directory != null){
				ftp.changeWorkingDirectory(directory);
			}
			
			FTPFile[] remotes = ftp.listFiles(remote);
			
			if(remotes.length == 0){
				throw new IOException(String.format("File [%s] not found!", remote));
			}
			if(remotes.length > 1){
				throw new IOException(String.format("File [%s] is not the only!", remote));
			}

			long size = remotes[0].getSize();
			long trans = localFile.exists()?localFile.length():0;
			
			if(trans > size){
				throw new IOException("Local file size more than remote file!");
			}
			
			if(trans > 0){
				ftp.setRestartOffset(trans);
			}
			
			if(trans == size){
				listener.update(trans, size);
			}
			OutputStream output = new FileOutputStream(local, true);
			InputStream input = ftp.retrieveFileStream(remote);
			
			byte[] bytes = new byte[ftp.getBufferSize()];
			int len;
			while((len = input.read(bytes)) != -1){
				output.write(bytes, 0, len);
				trans += len;
				if(listener != null){
					listener.update(trans, size);
				}
			}
			
			input.close();
            output.close();
            ftp.noop();
            ftp.completePendingCommand();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			throw new IOException(String.format("The file [%s] download failed!", remote));
		}finally{
			if(ftp != null && ftp.isConnected()){
				ftp.logout();
				ftp.disconnect();
			}
		}
	}
	
	
	private static boolean changeWorkingDirectory(FTPClient ftp, String path) throws IOException{
		if(ftp.changeWorkingDirectory(path)){
			return true;
		}else{
			List<String> dirs = new ArrayList<String>();
			String pathTemp = path;
			
			try {
				do{
					if(pathTemp.endsWith("/")){
						pathTemp = pathTemp.substring(0, pathTemp.length() - 1);
					}
					int index = pathTemp.lastIndexOf("/") + 1;
					if(pathTemp.endsWith("/")){
						continue;
					}
					dirs.add(pathTemp.substring(index));
					pathTemp = pathTemp.substring(0, index);
				}while(!ftp.changeWorkingDirectory(pathTemp));
				//倒序排列
				Collections.reverse(dirs);
				if(dirs != null && dirs.size() > 0){
					for(String dir : dirs){
						ftp.makeDirectory(dir);
						ftp.changeWorkingDirectory(dir);
					}
					return ftp.changeWorkingDirectory(path);
				}else{
					throw new IOException(String.format("The directory [%s] is not exist,please check the path!", pathTemp));
				}
			} catch (IOException e) {
				e.printStackTrace();
				throw new IOException(String.format("The directory [%s] is not exist,please check the path!", pathTemp),e);
			}
		}
	}
	/**
	 * 将FTP服务器上的文件重命名或移动到其它目录中
	 * eg:
	 * <p>
	 * FTPClientUtils.move("211.142.200.18:2121", "admin", "admin", "Readme.txt", "/359E9A79C99640EDAD14B8C704D927D4/Readme.txt");
	 * </p>
	 * 
	 * @param hostname	FTP主机地址
	 * @param username	用户
	 * @param password	密码
	 * @param from		要移动的文件
	 * @param to		要移动到的路径
	 * @return
	 * @throws Exception
	 */
	public static boolean move(String hostname, String username, String password, String from, String to) throws Exception{
		FTPClient ftp = null;
		try{
			ftp = connectServer(hostname,username,password);
			String directory = to.substring(0, to.lastIndexOf("/"));
			//获取当前工作目录
			ftp.pwd();
			String currentDirectory = ftp.getReplyString();
			Pattern pattern = Pattern.compile("\"(.*?)\"");
			Matcher matcher = pattern.matcher(currentDirectory);
			if(matcher.find()){
				currentDirectory = matcher.group().replaceAll("\"", "");
				currentDirectory = currentDirectory.endsWith("/") ? currentDirectory : currentDirectory + "/";
			}
			if(changeWorkingDirectory(ftp, directory)){
				return ftp.rename(from.startsWith("/")? from : currentDirectory + from, to);
			}
			return false;
		}catch(Exception e){
			throw new RuntimeException(e.getMessage(),e);
		}finally{
			if(ftp != null && ftp.isConnected()){
				ftp.logout();
				ftp.disconnect();
			}
		}
	}
	
	/**
	 * 将FTP服务器上的文件重命名或移动到其它目录中
	 * eg:
	 * <p>
	 * FTPClientUtils.move("211.142.200.18:2121", "admin", "admin", files, "/359E9A79C99640EDAD14B8C704D927D4");
	 * </p>
	 * 
	 * @param hostname	FTP主机地址
	 * @param username	用户
	 * @param password	密码
	 * @param files		要移动的文件
	 * @param to		要移动到的路径
	 * @return
	 * @throws Exception
	 */
	public static void move(String hostname, String username, String password, List<File> files, String to) throws Exception{
		FTPClient ftp = null;
		try{
			ftp = connectServer(hostname,username,password);
			if(changeWorkingDirectory(ftp, to)){
				for(File file : files){
					ftp.rename("/" + file.getName(), to + "/" + file.getName());
				}
			}
		}catch(Exception e){
			throw new RuntimeException(e.getMessage(),e);
		}finally{
			if(ftp != null && ftp.isConnected()){
				ftp.logout();
				ftp.disconnect();
			}
		}
	}
}
