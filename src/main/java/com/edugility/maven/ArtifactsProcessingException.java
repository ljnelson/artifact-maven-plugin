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

import java.io.Serializable; // for javadoc only

import java.util.Collection;

import org.apache.maven.artifact.Artifact;

/**
 * An {@link Exception} indicating that something has gone wrong
 * during {@linkplain ArtifactsProcessor#process(MavenProject,
 * Collection, Log) <code>Artifact</code> processing}.
 *
 * @author <a href="http://about.me/lairdnelson"
 * target="_parent">Laird Nelson</a>
 *
 * @see ArtifactsProcessor
 *
 * @see ArtifactMojo
 */
public class ArtifactsProcessingException extends Exception {

  /**
   * The version of this class for {@linkplain Serializable
   * serialization} purposes.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The {@link Collection} of {@link Artifact}s that caused this
   * {@link ArtifactsProcessingException} to be thrown.
   *
   * <p>This field may be {@code null}.</p>
   *
   * @see #setArtifacts(Collection)
   */
  private Collection<? extends Artifact> artifacts;

  /**
   * Creates a new {@link ArtifactsProcessingException}.
   */
  public ArtifactsProcessingException() {
    super();
  }

  /**
   * Creates a new {@link ArtifactsProcessingException}.
   *
   * @param message a message describing the error; may be {@code
   * null}
   */
  public ArtifactsProcessingException(final String message) {
    super(message);
  }

  /**
   * Creates a new {@link ArtifactsProcessingException}.
   *
   * @param cause the {@link Throwable} that caused this {@link
   * ArtifactsProcessingException} to be thrown; may be {@code null}
   */
  public ArtifactsProcessingException(final Throwable cause) {
    super(cause);
  }

  /**
   * Returns the {@link Collection} of {@link Artifact}s that caused
   * this {@link ArtifactsProcessingException} to be thrown.
   *
   * <p>This method may return {@code null}.</p>
   *
   * @return the {@link Collection} of {@link Artifact}s that caused
   * this {@link ArtifactsProcessingException} to be thrown, or {@code
   * null}
   *
   * @see #setArtifacts(Collection)
   */
  public Collection<? extends Artifact> getArtifacts() {
    return this.artifacts;
  }

  /**
   * Sets the {@link Collection} of {@link Artifact}s that caused this
   * {@link ArtifactsProcessingException} to be thrown.
   *
   * @param artifacts the {@link Collection} of {@link Artifact}s that
   * caused this {@link ArtifactsProcessingException} to be thrown;
   * may be {@code null}
   *
   * @see #getArtifacts()
   */
  public void setArtifacts(final Collection<? extends Artifact> artifacts) {
    this.artifacts = artifacts;
  }

}
