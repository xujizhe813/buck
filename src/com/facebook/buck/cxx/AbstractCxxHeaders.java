/*
 * Copyright 2014-present Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.facebook.buck.cxx;

import com.facebook.buck.rules.RuleKey;
import com.facebook.buck.rules.RuleKeyAppendable;
import com.facebook.buck.rules.SourcePath;
import com.facebook.buck.util.immutables.BuckStyleImmutable;
import com.google.common.collect.ImmutableSortedSet;

import org.immutables.value.Value;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Value.Immutable
@BuckStyleImmutable
abstract class AbstractCxxHeaders implements RuleKeyAppendable {

  /**
   * List of headers that are implicitly included at the beginning of each preprocessed source file.
   */
  abstract List<SourcePath> getPrefixHeaders();

  /**
   * Maps the name of the header (e.g. the path used to include it in a C/C++ source) to the
   * real location of the header.
   */
  abstract Map<Path, SourcePath> getNameToPathMap();

  /**
   * Maps the full of the header (e.g. the path to the header that appears in error messages) to
   * the real location of the header.
   */
  abstract Map<Path, SourcePath> getFullNameToPathMap();

  @Override
  public RuleKey.Builder appendToRuleKey(RuleKey.Builder builder) {
    builder.setReflectively("prefixHeaders", getPrefixHeaders());

    for (Path path : ImmutableSortedSet.copyOf(getNameToPathMap().keySet())) {
      SourcePath source = getNameToPathMap().get(path);
      builder.setReflectively("include(" + path + ")", source);
    }

    return builder;
  }
}
