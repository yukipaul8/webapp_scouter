/*
 *  Copyright 2015 the original author or authors.
 *  @https://github.com/scouter-project/scouter
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package scouterx.webapp.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import scouterx.webapp.framework.client.server.ServerManager;
import scouterx.webapp.framework.exception.ErrorState;

import javax.validation.constraints.NotNull;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;

/**
 * @author Gun Lee (gunlee01@gmail.com) on 2017. 8. 27.
 */
@Getter
@Setter
@ToString
public class LatestCounterRequest {
    private final int limitRangeSec = 60 * 60; //1 hour

    private int serverId;

    @NotNull
    @PathParam("counter")
    private String counter;

    @NotNull
    @PathParam("latestSec")
    private int latestSec;

    @QueryParam("serverId")
    public void setServerId(int serverId) {
        this.serverId = ServerManager.getInstance().getServerIfNullDefault(serverId).getId();
    }

    public void validate() {
        if (latestSec > limitRangeSec) {
            throw ErrorState.VALIDATE_ERROR.newBizException("query range should be lower than " + limitRangeSec + " seconds!");
        }
    }
}
