
package Resorce;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Vector;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class FileNodeOperation 
{
	private Table table = null;
	private Tree tree = null;
	private MyNode node = null;
	private Vector<Vector<MyNode>> copiedList = new Vector<Vector<MyNode>>();
	private Vector<MyNode> copyFile = null;
	private Vector<MyNode> multiSelectedNodeList = new Vector<MyNode>();
	private Vector<String> pastedFilePath = new Vector<String>();
	private static Vector<MyNode> searchNodeList = new Vector<MyNode>();
	private int index = 0;
	private int pos = 0;
	private int newNodeIndex = 0;

	public FileNodeOperation() 
	{

	}

	public FileNodeOperation(Table table) 
	{
		this.table = table;
	}

	public FileNodeOperation(Tree tree) 
	{
		this.tree = tree;
	}

	private void setCopyList(MyNode copiedNode) 
	{
		if (index++ == 0) 
		{
			copyFile = new Vector<MyNode>();
			copyFile.add(copiedNode);

			copiedList.add(copyFile);
			pos = copiedNode.getPath().lastIndexOf(File.separator);
		}

		File[] copieFiles = copiedNode.getFile().listFiles();

		if (copieFiles != null) 
		{
			for (File file : copieFiles) 
			{
				if (file.isDirectory()) 
				{
					copyFile = new Vector<MyNode>();

					MyNode node_ = new TreeNode(file);
					copyFile.add(node_);
					copiedList.add(copyFile);

					copiedList.removeAllElements();
				} 
				else 
				{
					copyFile = new Vector<MyNode>();

					MyNode node_ = new TreeNode(file);
					copyFile.add(node_);
					copiedList.add(copyFile);
				}
			}
		}
	}

	public void copy(MyNode copiedFileNode, MyNode pastedFileNode) 
	{
		try 
		{
			setCopyList(copiedFileNode);

			for (Vector<MyNode> copyFile : copiedList) 
			{
				String copyPath = copyFile.get(0).getPath();
				String pastePath = pastedFileNode.getPath()
						+ copyPath.substring(pos);

				pastedFilePath.add(copyPath.substring(pos));

				if (copyFile.get(0).getFile().isFile()) 
				{
					copyFile(copyPath, pastePath);
				} 
				else if (copyFile.get(0).getFile().isDirectory()) 
				{
					new File(pastePath).mkdir();
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	private void copyFile(String copiedPath, String pastedPath) 
	{
		try 
		{
			File file = new File(copiedPath);
			FileInputStream inputStream = new FileInputStream(file);

			File pastedFile = new File(pastedPath);
			FileOutputStream outputStream = new FileOutputStream(pastedFile);

			byte[] buffer = new byte[10240];
			int len = 0;

			while ((len = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, len);
			}

			outputStream.close();
			inputStream.close();

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}


	public Vector<MyNode> getPastedNode(MyNode pastedFileNode) 
	{
		Vector<MyNode> pastedNodeList = new Vector<MyNode>();

		if (pastedNodeList.size() != 0) 
		{
			pastedNodeList.removeAllElements();
		}

		for (String path : pastedFilePath) 
		{
			String pastedNodePath = pastedFileNode.getPath() + path;
			if (path.lastIndexOf(File.separator) == 0) 
			{
				MyNode pastedNode = new TreeNode(new File(pastedNodePath));
				pastedNodeList.add(pastedNode);
			}
		}
		return pastedNodeList;
	}

	public void setFileNode(MyNode node) 
	{
		index = 0;
		if (copiedList.size() != 0) 
		{
			copiedList.removeAllElements();
		}

		this.node = node;
	}
	public void resetPastedFilePath() 
	{
		pastedFilePath.removeAllElements();
	}
	public void resetFileNode() 
	{
		this.node = null;
	}
	public void setFileNodeList(MyNode node) 
	{
		multiSelectedNodeList.add(node);
	}
	public boolean isClipboardEmpty() {
		return (this.node == null && multiSelectedNodeList.size() == 0);
	}
	public static void setSearchNodeList(MyNode node) {
		searchNodeList.add(node);
	}
	public static Vector<MyNode> getSearchNodeList() {
		return searchNodeList;
	}
	public void removeAllFileNode() {
		if (multiSelectedNodeList.size() != 0) {
			multiSelectedNodeList.removeAllElements();
		}
	}
	
	public Vector<MyNode> getFileNodeList() {
		return this.multiSelectedNodeList;
	}
	public MyNode getFileNode() {
		return node;
	}
	public void delete(MyNode node) {
		File file = node.getFile();
		deleteFile(file);
		file.delete();
	}

	public String rename(MyNode node, String newName) {
		if (node != null) {
			File file = node.getFile();
			String path = ((MyNode) node.getParent()).getPath()
					+ File.separator + newName;

			file.renameTo(new File(path));

			return path;
		} else {
			return "";
		}
	}

	private void deleteFile(File deletedFile) {
		if (deletedFile.isDirectory() && deletedFile.listFiles() != null) {
			File[] fileList = deletedFile.listFiles();

			for (File file : fileList) {
				if (file.isDirectory()) {
					deleteFile(new File(file.getPath()));
				} else {
					file.delete();
				}

				file.delete();
			}
		} else {
			deletedFile.delete();
		}
	}

	public void createNewFolder() {
		String name = node.getPath() + File.separator ;
		int index = getNewFolderIndex(name);

		if (index != 0) {
			name = name + "(" + (index + 1) + ")";
		}

		File folder = new File(name);
		folder.mkdir();

		MyNode newFolder = new TreeNode(folder);
		node.addChild(newFolder);

		newNodeIndex = 0;
	}

	private int getNewFolderIndex(String folder) {
		File file = new File(folder);

		if (file.exists()) {
			newNodeIndex++;
			if (file.getName().indexOf("(") > 0) {
				int postion = file.getPath().lastIndexOf("(");
				String path = file.getPath().substring(0, postion) + "("
						+ (newNodeIndex + 1) + ")";
				getNewFolderIndex(path);
			} else {
				String path = file.getPath() + "(" + (newNodeIndex + 1) + ")";
				getNewFolderIndex(path);
			}
		}

		return newNodeIndex;
	}

	public void createNewFile(String type) 
	{
		String name = null;

		File file = new File(name);
		try {
			file.createNewFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		MyNode newFile = new TreeNode(file);
		node.addChild(newFile);

		newNodeIndex = 0;
	}

	private int getNewFileIndex(String filePath) 
	{
		File file = new File(filePath);

		if (file.exists()) {
			newNodeIndex++;
			if (file.getName().indexOf("(") > 0) {
				int postion = file.getPath().lastIndexOf("(");
				String path = file.getPath().substring(0, postion) + "("
						+ (newNodeIndex + 1) + ")"
						+ filePath.substring(filePath.lastIndexOf("."));

				getNewFileIndex(path);
			} else {
				String path = file.getPath().substring(0,
						file.getPath().lastIndexOf("."));
				path = path
						+ "("
						+ (newNodeIndex + 1)
						+ ")"
						+ file.getPath().substring(
								file.getPath().lastIndexOf("."));
				getNewFileIndex(path);
			}
		}

		return newNodeIndex;
	}

	public Icon getIcon(String iconName) 
	{
		String currdir = null;
		try {
			currdir = new File(".").getCanonicalPath();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ImageIcon(this.getClass().getResource("/icon/" + iconName));
	}
}
