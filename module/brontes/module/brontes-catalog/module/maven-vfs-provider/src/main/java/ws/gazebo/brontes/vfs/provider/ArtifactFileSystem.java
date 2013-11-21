package ws.gazebo.brontes.vfs.provider;

import java.util.Collection;

import org.apache.commons.vfs2.Capability;
import org.apache.commons.vfs2.FileName;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.provider.AbstractFileName;
import org.apache.commons.vfs2.provider.AbstractFileSystem;

public class ArtifactFileSystem extends AbstractFileSystem {

	protected ArtifactFileSystem(FileName rootName, FileObject parentLayer,
			FileSystemOptions fileSystemOptions) {
		super(rootName, parentLayer, fileSystemOptions);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected FileObject createFile(AbstractFileName name) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void addCapabilities(Collection<Capability> caps) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    public void init() throws FileSystemException {
		
	}

}
