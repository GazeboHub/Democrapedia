package ws.gazebo.brontes.logging.hibernate.impl;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

public class HibernateAppender extends AppenderSkeleton {

	/*
	 * Note: In a database-connected log appender, the concern for transaction
	 * management (cf. connection pooling) may present something of a
	 * "tricky matter". Some notes, towards which effect:
	 * 
	 * -- Ideally, it should be possible for the application to provide "hints"
	 * as to the start an end of a single transactional context
	 * 
	 * ---- That may be addressed toward supporting a paradigm in which an
	 * application could "hint" the start of (for instance) an "application
	 * startup" context, such as on entry into main(), and then to note the end
	 * of the startup context as on layered entry into regular application
	 * procedures
	 * 
	 * ---- Hypothetically, a set of annotations may be developed within this
	 * module, such as to support a paradigm of such
	 * "transaction bounds hinting" namely within the application of the logging
	 * component of a containing application
	 * 
	 * -- For "Proof of precedent", with regards to available patterns for
	 * transaction management in a database-connected log appender, refer to
	 * org.apache.log4j.jdbc.JDBCAppender
	 * 
	 * -- Also note Appender#doAppend(...) as defined in AppenderSkeleton.
	 * Ideally, here in HibernateAppender the method would be "Advised" such
	 * that a transaction would be ensured as to be initialized before the call
	 * to the superclass doAppend(...), and then flushed after the call returns.
	 */

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	/**
	 * @return false, constantly
	 */
	@Override
	public boolean requiresLayout() {
		return false;
	}

	@Override
	protected void append(LoggingEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public synchronized void doAppend(LoggingEvent event) {
		Session s = ensureTransactionStart();
		super.doAppend(event);
		s.flushIfWasNewInThisMethod();
	}

	
}
