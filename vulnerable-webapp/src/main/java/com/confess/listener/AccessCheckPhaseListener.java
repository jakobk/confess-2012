/*
 * Copyright 2011-2012, Jakob Korherr
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.confess.listener;

import com.confess.bean.UserBean;

import javax.faces.application.Application;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import java.io.IOException;

/**
 * PhaseListener checking for valid user when accessing internal pages.
 *
 * @author Jakob Korherr
 */
public class AccessCheckPhaseListener implements PhaseListener
{

    public void afterPhase(PhaseEvent phaseEvent)
    {
        if (phaseEvent.getPhaseId() == PhaseId.RESTORE_VIEW)
        {
            checkUrlAccess(phaseEvent.getFacesContext());
        }
    }

    public void beforePhase(PhaseEvent phaseEvent)
    {
        if (phaseEvent.getPhaseId() == PhaseId.RENDER_RESPONSE)
        {
            checkUrlAccess(phaseEvent.getFacesContext());
        }
    }

    public PhaseId getPhaseId()
    {
        return PhaseId.ANY_PHASE;
    }

    private void checkUrlAccess(FacesContext facesContext)
    {
        UIViewRoot viewRoot = facesContext.getViewRoot();
        if (viewRoot != null && viewRoot.getViewId().contains("/internal/"))
        {
            Application application = facesContext.getApplication();
            UserBean userBean = application.evaluateExpressionGet(facesContext, "#{userBean}", UserBean.class);

            if (!userBean.isAuthenticated())
            {
                try
                {
                    facesContext.getExternalContext().responseSendError(401, "Unauthorized");
                }
                catch (IOException e)
                {
                    // nop
                }
                facesContext.responseComplete();
            }
        }
    }

}
