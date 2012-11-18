/*******************************************************************************
 * Copyright (c) 2012 Tasktop Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Tasktop Technologies - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylyn.tasks.tests.ui;

import junit.framework.TestCase;

import org.eclipse.mylyn.internal.tasks.core.TaskComment;
import org.eclipse.mylyn.internal.tasks.core.TaskTask;
import org.eclipse.mylyn.tasks.core.ITask;
import org.eclipse.mylyn.tasks.core.ITaskComment;
import org.eclipse.mylyn.tasks.core.TaskRepository;
import org.eclipse.mylyn.tasks.core.data.TaskAttribute;
import org.eclipse.mylyn.tasks.core.data.TaskAttributeMapper;
import org.eclipse.mylyn.tasks.core.data.TaskData;
import org.eclipse.mylyn.tasks.tests.connector.MockRepositoryConnector;
import org.eclipse.mylyn.tasks.tests.connector.MockRepositoryConnectorUi;
import org.eclipse.mylyn.tasks.tests.connector.MockTask;
import org.eclipse.mylyn.tasks.ui.AbstractRepositoryConnectorUi;

/**
 * @author Benjamin Muskalla
 */
public class AbstractRepositoryConnectorUiTest extends TestCase {

	private AbstractRepositoryConnectorUi connectorUi;

	private TaskRepository repository;

	private TaskAttribute commentAttribute;

	private MockTask task;

	@Override
	protected void setUp() throws Exception {
		connectorUi = new MockRepositoryConnectorUi();
		repository = new TaskRepository(MockRepositoryConnector.CONNECTOR_KIND, MockRepositoryConnector.REPOSITORY_URL);
		task = new MockTask("1");
		TaskAttributeMapper mapper = new TaskAttributeMapper(repository);
		TaskData taskData = new TaskData(mapper, MockRepositoryConnector.CONNECTOR_KIND,
				MockRepositoryConnector.REPOSITORY_URL, "1");
		commentAttribute = taskData.getRoot().createAttribute("comment");
	}

	public void testGetReplyTextDescription() throws Exception {
		ITask task = new TaskTask("abc", "http://eclipse.org/mylyn", "1");
		String replyText = connectorUi.getReplyText(null, task, null, false);
		assertEquals("(In reply to comment #0)", replyText);
	}

	public void testGetReplyTextSpecificComment() throws Exception {
		ITaskComment taskComment = new TaskComment(repository, task, commentAttribute) {
			@Override
			public int getNumber() {
				return 13;
			}
		};
		String replyText = connectorUi.getReplyText(null, task, taskComment, false);
		assertEquals("(In reply to comment #13)", replyText);
	}

	public void testGetReplyTextSpecificCommentOnTask() throws Exception {
		ITaskComment taskComment = new TaskComment(repository, task, commentAttribute) {
			@Override
			public int getNumber() {
				return 13;
			}
		};
		String replyText = connectorUi.getReplyText(null, task, taskComment, true);
		assertEquals("(In reply to 1 comment #13)", replyText);
	}
}