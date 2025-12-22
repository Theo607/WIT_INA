/**
 * Copyright 2011 Joao Miguel Pereira
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package eu.jpereira.trainings.designpatterns.creational.abstractfactory;

import eu.jpereira.trainings.designpatterns.creational.abstractfactory.json.JSONReportBody;
import eu.jpereira.trainings.designpatterns.creational.abstractfactory.json.JSONReportFooter;
import eu.jpereira.trainings.designpatterns.creational.abstractfactory.json.JSONReportHeader;
import eu.jpereira.trainings.designpatterns.creational.abstractfactory.xml.XMLReportBody;
import eu.jpereira.trainings.designpatterns.creational.abstractfactory.xml.XMLReportFooter;
import eu.jpereira.trainings.designpatterns.creational.abstractfactory.xml.XMLReportHeader;

public class Report {

    private ReportHeader header;
    private ReportBody body;
    private ReportFooter footer;

    private String reportContent;

    public Report(ReportFactory factory) {
        this.header = factory.createHeader();
        this.body = factory.createBody();
        this.footer = factory.createFooter();
    }

    public Report(String reportType) {
      this(reportType.equals("XML") ? new XMLReportFactory() : new JSONReportFactory());
    }

    public ReportHeader getHeader() { return header; }
    public ReportBody getBody() { return body; }
    public ReportFooter getFooter() { return footer; }

    public void setReportContent(String content) { this.reportContent = content; }
    public String getReportContent() { return reportContent; }
}
