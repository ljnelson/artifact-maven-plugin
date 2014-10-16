/* -*- mode: Java; c-basic-offset: 2; indent-tabs-mode: nil; coding: utf-8-unix -*-
 *
 * Copyright (c) 2013-2014 Edugility LLC.
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense and/or sell copies
 * of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THIS SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT.  IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 * The original copy of this license is available at
 * http://www.opensource.org/license/mit-license.html.
 */
package com.edugility.maven;

import java.io.File;

import java.util.Collection;

import com.edugility.maven.Artifacts;

import org.apache.maven.artifact.Artifact;

import org.apache.maven.artifact.resolver.ArtifactResolver;
import org.apache.maven.artifact.resolver.ArtifactResolutionException;

import org.apache.maven.artifact.resolver.filter.ArtifactFilter;

import org.apache.maven.artifact.repository.ArtifactRepository;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import org.apache.maven.plugin.logging.Log;

import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;

import org.apache.maven.project.MavenProject;

import org.apache.maven.shared.dependency.graph.DependencyGraphBuilder;
import org.apache.maven.shared.dependency.graph.DependencyGraphBuilderException;

/**
 * A mojo that sorts Maven artifacts topologically and then arranges
 * the processing of them with a user-supplied processor.
 *
 * @author <a href="http://about.me/lairdnelson"
 * target="_parent">Laird Nelson</a>
 *
 * @see ArtifactsProcessor
 */
@Mojo(name = "process")
public class ArtifactMojo extends AbstractMojo {

  /**
   * The {@link MavenProject} in effect.
   */
  @Component
  private MavenProject project;

  /**
   * The {@link DependencyGraphBuilder} used to collect all
   * dependencies of a project.
   */
  @Component
  private DependencyGraphBuilder dependencyGraphBuilder;

  /**
   * The {@link ArtifactResolver} to use when a given artifact has not
   * yet been resolved.
   */
  @Component
  private ArtifactResolver resolver;

  /**
   * An {@link ArtifactsProcessor} implementation that will receive a
   * topologically sorted collection of resolved Maven artifacts.
   */
  @Parameter(required = true)
  private ArtifactsProcessor artifactsProcessor;

  /**
   * The local {@link ArtifactRepository} in effect.
   */
  @Parameter(defaultValue = "${localRepository}", readonly = true)
  private ArtifactRepository localRepository;

  /**
   * An {@link ArtifactFilter} for choosing what {@link Artifact}s are
   * to be processed.
   */
  @Parameter
  private ArtifactFilter artifactFilter;

  /**
   * Creates a new {@link ArtifactMojo}.
   */
  public ArtifactMojo() {
    super();
  }

  /**
   * Collects all {@link Artifact}s that should be
   * processed&mdash;according to the {@linkplain #artifactFilter
   * associated <code>ArtifactFilter</code>}&mdash;and hands them off
   * to the associated {@link #artifactsProcessor ArtifactsProcessor}.
   *
   * @exception MojoExecutionException if processing failed
   *
   * @exception MojoFailureException if setup failed
   *
   * @see ArtifactsProcessor
   */
  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    if (this.project == null) {
      throw new MojoFailureException("this.project == null", new IllegalStateException("this.project == null", new NullPointerException("this.project")));
    }
    if (this.dependencyGraphBuilder == null) {
      throw new MojoFailureException("this.dependencyGraphBuilder == null", new IllegalStateException("this.dependencyGraphBuilder == null", new NullPointerException("this.dependencyGraphBuilder")));
    }

    final ArtifactsProcessor processor = this.getArtifactsProcessor();
    if (processor == null) {
      throw new MojoFailureException("this.getArtifactsProcessor() == null", new IllegalStateException("this.getArtifactsProcessor() == null", new NullPointerException("this.getArtifactsProcessor()")));
    }

    Collection<? extends Artifact> artifacts = null;

    try {
      artifacts = new Artifacts().getArtifactsInTopologicalOrder(this.project, 
                                                                 this.dependencyGraphBuilder,
                                                                 this.artifactFilter,
                                                                 resolver,
                                                                 localRepository);
    } catch (final DependencyGraphBuilderException wrapMe) {
      throw new MojoExecutionException("Failed to get dependency artifacts", wrapMe);
    } catch (final ArtifactResolutionException wrapMe) {
      throw new MojoExecutionException("Failed to get dependency artifacts", wrapMe);
    }

    if (artifacts != null && !artifacts.isEmpty()) {
      try {
        processor.process(this.project, artifacts, this.getLog());
      } catch (final ArtifactsProcessingException wrapMe) {
        throw new MojoExecutionException("Failed to process artifacts", wrapMe);
      }
    }
  }

  /**
   * Returns an {@link ArtifactsProcessor} for use by the {@link
   * #execute()} method.
   *
   * <p>This method may return {@code null}.</p>
   *
   * @return an {@link ArtifactsProcessor}
   *
   * @see ArtifactsProcessor
   */
  public ArtifactsProcessor getArtifactsProcessor() {
    return this.artifactsProcessor;
  }

}
