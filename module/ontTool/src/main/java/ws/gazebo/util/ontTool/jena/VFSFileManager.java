package ws.gazebo.util.ontTool.jena;

import java.io.File;

import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.VFS;

import com.hp.hpl.jena.util.FileManager;

public class VFSFileManager extends FileManager {

	private FileSystemManager fsMgr;
	private File baseFile;

	public VFSFileManager() {
		this(new File(System.getProperty("user.dir")));
	}

	public VFSFileManager(File baseFile) {
		super();
		try {
			fsMgr = VFS.getManager();
		} catch (FileSystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.baseFile = baseFile;
	}

	@Override
	public String mapURI(String filenameOrURI) {
		System.err.println("Mapping input " + filenameOrURI);
		FileObject f;
		try {
			f = fsMgr.resolveFile(baseFile, filenameOrURI);
			String r = f.getURL().toString();
			System.err.println("..mapped to " + r);
			return r;
		} catch (FileSystemException e) {
			e.printStackTrace();
			return null;
		}
	}
}
