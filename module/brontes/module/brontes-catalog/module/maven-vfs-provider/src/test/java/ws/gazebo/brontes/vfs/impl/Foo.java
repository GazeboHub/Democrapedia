package ws.gazebo.brontes.vfs.impl;

import org.apache.commons.vfs2.impl.DefaultFileSystemManager;
import org.apache.commons.vfs2.impl.StandardFileSystemManager;

public class Foo {
	static String THE_LOCAL_VFS_CONFIG;
	static {
		StandardFileSystemManager mgr = new StandardFileSystemManager();
		mgr.setConfiguration(THE_LOCAL_VFS_CONFIG);
	}

}
