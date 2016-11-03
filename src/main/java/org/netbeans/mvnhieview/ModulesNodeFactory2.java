package org.netbeans.mvnhieview;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.event.ChangeListener;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectUtils;
import org.netbeans.spi.project.SubprojectProvider;
import org.netbeans.spi.project.ui.LogicalViewProvider;
import org.netbeans.spi.project.ui.support.NodeFactory;
import org.netbeans.spi.project.ui.support.NodeList;
import org.openide.nodes.FilterNode;
import org.openide.nodes.Node;

@NodeFactory.Registration(projectType = "org-netbeans-modules-maven", position = 400)
public class ModulesNodeFactory2 implements NodeFactory {

   @Override
   public NodeList<?> createNodes(Project project) {
      return new MavenModulesNodeList(project);
   }

   private class MavenModulesNodeList implements NodeList<Project> {

      private final Project project;

      public MavenModulesNodeList(Project project) {
         this.project = project;
      }

      @Override
      public List<Project> keys() {

         ArrayList<Project> projects = new ArrayList<>(project.getLookup().lookup(SubprojectProvider.class).getSubprojects());

         projects.sort(new Comparator<Project>() {
            @Override
            public int compare(Project project1, Project project2) {
               return ProjectUtils.getInformation(project1).getDisplayName().compareTo(ProjectUtils.getInformation(project2).getDisplayName());
            }
         });

         return projects;
      }

      @Override
      public Node node(final Project project) {
         Node node = project.getLookup().lookup(LogicalViewProvider.class).createLogicalView();
         return new FilterNode(node, new FilterNode.Children(node));
      }

      @Override
      public void addChangeListener(ChangeListener changeListener) {
      }

      @Override
      public void removeChangeListener(ChangeListener changeListener) {
      }

      @Override
      public void addNotify() {
      }

      @Override
      public void removeNotify() {
      }

   }

}
