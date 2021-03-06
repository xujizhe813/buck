/*
 * Copyright 2015-present Facebook, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may
 *  not use this file except in compliance with the License. You may obtain
 *  a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations
 *  under the License.
 */

package com.facebook.buck.js;

import com.facebook.buck.model.Flavor;
import com.facebook.buck.model.Flavored;
import com.facebook.buck.rules.BuildRule;
import com.facebook.buck.rules.BuildRuleParams;
import com.facebook.buck.rules.BuildRuleResolver;
import com.facebook.buck.rules.BuildRuleType;
import com.facebook.buck.rules.Description;
import com.google.common.annotations.Beta;
import com.google.common.collect.ImmutableSet;

@Beta
public class AndroidReactNativeLibraryDescription
    implements Description<ReactNativeLibraryArgs>, Flavored {

  private static final BuildRuleType TYPE = BuildRuleType.of("android_react_native_library");

  private final ReactNativeLibraryGraphEnhancer enhancer;

  public AndroidReactNativeLibraryDescription(ReactNativeBuckConfig buckConfig) {
    this.enhancer = new ReactNativeLibraryGraphEnhancer(buckConfig);
  }

  @Override
  public BuildRuleType getBuildRuleType() {
    return TYPE;
  }

  @Override
  public ReactNativeLibraryArgs createUnpopulatedConstructorArg() {
    return new ReactNativeLibraryArgs();
  }

  @Override
  public <A extends ReactNativeLibraryArgs> BuildRule createBuildRule(
      BuildRuleParams params,
      BuildRuleResolver resolver,
      A args) {
    return enhancer.enhance(params, resolver, args, ReactNativePlatform.ANDROID);
  }

  @Override
  public boolean hasFlavors(ImmutableSet<Flavor> flavors) {
    return ReactNativeFlavors.validateFlavors(flavors);
  }
}
