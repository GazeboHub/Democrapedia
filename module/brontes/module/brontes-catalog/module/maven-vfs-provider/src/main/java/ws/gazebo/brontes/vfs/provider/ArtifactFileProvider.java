package ws.gazebo.brontes.vfs.provider;

import java.util.Collection;

import org.apache.commons.vfs2.Capability;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileSystem;
import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemOptions;
import org.apache.commons.vfs2.provider.AbstractLayeredFileProvider;
import org.apache.commons.vfs2.provider.FileProvider;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolverSystem;

public class ArtifactFileProvider extends AbstractLayeredFileProvider {

	protected static MavenResolverSystem resolverSystem = Maven.resolver();
	
	@Override
	public Collection<Capability> getCapabilities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected FileSystem doCreateFileSystem(String scheme, FileObject file,
			FileSystemOptions fileSystemOptions) throws FileSystemException {
		// TODO Auto-generated method stub
		return null;
	}

}
