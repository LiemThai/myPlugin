package com.atlassian.tutorial.myPlugin;

import com.atlassian.jira.web.action.JiraWebActionSupport;

public class Choose extends JiraWebActionSupport {

    public Choose() {
    }

    protected void doValidation() {

    }

    protected String doExecute() throws Exception {
        return SUCCESS;
    }

    public String doDefault() throws Exception {
        return super.doDefault();
    }
}