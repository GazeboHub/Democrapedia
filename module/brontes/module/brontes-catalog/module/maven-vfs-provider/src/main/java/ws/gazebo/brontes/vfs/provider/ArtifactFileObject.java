package ws.gazebo.brontes.vfs.provider;

import java.io.InputStream;

import org.apache.commons.vfs2.FileType;
import org.apache.commons.vfs2.provider.AbstractFileName;
import org.apache.commons.vfs2.provider.AbstractFileObject;
import org.apache.commons.vfs2.provider.AbstractFileSystem;

public class ArtifactFileObject extends AbstractFileObject {

	protected ArtifactFileObject(AbstractFileName name, AbstractFileSystem fs) {
		super(name, fs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected FileType doGetType() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String[] doListChildren() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected long doGetContentSize() throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected InputStream doGetInputStream() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
