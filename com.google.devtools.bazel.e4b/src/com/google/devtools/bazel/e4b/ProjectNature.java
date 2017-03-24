// Copyright 2016 The Bazel Authors. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.devtools.bazel.e4b;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

/**
 * Project nature for e4b.
 */
public class ProjectNature implements IProjectNature {

  public static final String ID = "com.google.devtools.bazel.e4b.projectNature"; //$NON-NLS-1$
  private IProject project;

  @Override
  public void configure() throws CoreException {
    IProjectDescription desc = project.getDescription();
    List<ICommand> commands = new ArrayList<ICommand>(Arrays.asList(desc.getBuildSpec()));
    Iterator<ICommand> iterator = commands.iterator();
    while (iterator.hasNext()) {
      ICommand command = iterator.next();
      if (ProjectNature.ID.equals(command.getBuilderName())) {
        return;
      }
    }
    ICommand newCommand = desc.newCommand();
    newCommand.setBuilderName(ProjectNature.ID);
    commands.add(newCommand);
    desc.setBuildSpec(commands.toArray(new ICommand[0]));
    project.setDescription(desc, null);
  }

  @Override
  public void deconfigure() throws CoreException {
    IProjectDescription desc = project.getDescription();
    List<ICommand> commands = new ArrayList<ICommand>(Arrays.asList(desc.getBuildSpec()));
    Iterator<ICommand> iterator = commands.iterator();
    while (iterator.hasNext()) {
      ICommand command = iterator.next();
      if (ProjectNature.ID.equals(command.getBuilderName())) {
        iterator.remove();
      }
    }
    desc.setBuildSpec(commands.toArray(new ICommand[0]));
    project.setDescription(desc, null);
  }

  @Override
  public IProject getProject() {
    return project;
  }

  @Override
  public void setProject(IProject project) {
    this.project = project;
  }
}
