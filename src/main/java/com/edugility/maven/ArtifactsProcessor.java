/* -*- mode: Java; c-basic-offset: 2; indent-tabs-mode: nil; coding: utf-8-unix -*-
 *
 * Copyright (c) 2014 Edugility LLC.
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

import java.util.Collection;

import org.apache.maven.artifact.Artifact;

import org.apache.maven.plugin.logging.Log;

import org.apache.maven.project.MavenProject;

/**
 * A processor of Maven {@link Artifact}s.
 *
 * @author <a href="http://about.me/lairdnelson"
 * target="_parent">Laird Nelson</a>
 *
 * @see ArtifactMojo
 */
public interface ArtifactsProcessor {

  /**
   * Performs some operation on the supplied {@link Collection} of
   * {@link Artifact}s in the context of the supplied {@link
   * MavenProject} and returns a {@link Collection} of {@link
   * Artifact}s representing the result.
   *
   * <p>Implementations of this method are permitted to return {@code
   * null}.</p>
   *
   * <p>Implementations of this method are permitted to return the
   * supplied {@link Collection} of {@link Artifact}s.</p>
   *
   * <p>Implementations of this method are permitted to mutate the
   * supplied {@link Collection} and/or any of its elements.</p>
   *
   * <p>Implementations of this method must not mutate the supplied
   * {@link MavenProject}.</p>
   *
   * @param project the {@link MavenProject} in the context of which
   * processing will take place; must not be {@code null}
   *
   * @param artifacts the {@link Artifact}s to process; may be {@code
   * null}
   *
   * @param log the {@link Log} to use when logging; may be {@code
   * null}
   *
   * @return a {@link Collection} of {@link Artifact}s representing
   * the result of processing, or {@code null}
   *
   * @exception ArtifactsProcessingException if an error occurs
   *
   * @see ArtifactMojo
   *
   * @see Artifact
   * 
   * @see MavenProject
   */
  public Collection<? extends Artifact> process(final MavenProject project, final Collection<? extends Artifact> artifacts, final Log log) throws ArtifactsProcessingException;

}
