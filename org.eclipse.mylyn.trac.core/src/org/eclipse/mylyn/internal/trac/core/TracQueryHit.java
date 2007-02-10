/*******************************************************************************
 * Copyright (c) 2006 - 2006 Mylar eclipse.org project and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Mylar project committers - initial API and implementation
 *******************************************************************************/

package org.eclipse.mylar.internal.trac.core;

import org.eclipse.mylar.tasks.core.AbstractQueryHit;
import org.eclipse.mylar.tasks.core.AbstractRepositoryTask;
import org.eclipse.mylar.tasks.core.TaskList;

/**
 * @author Steffen Pingel
 */
public class TracQueryHit extends AbstractQueryHit {

	public TracQueryHit(TaskList taskList, String repositoryUrl, String description, String id) {
		super(taskList, repositoryUrl, description, id);
	}

//	public TracQueryHit(TaskList taskList, String handle) {
//		super(taskList, RepositoryTaskHandleUtil.getRepositoryUrl(handle), "", RepositoryTaskHandleUtil.getTaskId(handle));
//	} 

	@Override
	protected AbstractRepositoryTask createTask() {
		TracTask newTask = new TracTask(this.getRepositoryUrl(), this.getTaskId(), getSummary(), true);
		newTask.setPriority(priority);
		return newTask;
	}

	@Override
	public String getUrl() {
		return getRepositoryUrl() + ITracClient.TICKET_URL + getTaskId();
	}

}
