/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.resourceserver.authentication.JwtAccessTokenAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Josh Cummings
 */
@SpringBootApplication
public class OktaApplication {
	@RestController
	public class SimpleController {

		@GetMapping("/ok")
		@PreAuthorize("@oauth2.hasScope(authentication, 'ok')")
		public String ok() {
			return "ok";
		}

		@GetMapping("/authenticated")
		public String okay(@AuthenticationPrincipal Authentication auth) {
			if ( auth instanceof JwtAccessTokenAuthenticationToken ) {
				return ((JwtAccessTokenAuthenticationToken) auth).getJwt().getSubject();
			}
			return null;
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(OktaApplication.class, args);
	}
}