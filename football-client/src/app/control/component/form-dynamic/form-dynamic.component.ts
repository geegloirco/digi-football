import {Component, NgZone, OnInit} from '@angular/core';
import {ActivatedRoute, Params} from "@angular/router";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {MessageService} from "../../service/message/message.service";
import {WaitingService} from "../../service/waiting/waiting.service";
import {ServerInfoService} from "../../../service/server-info/server-info.service";


@Component({
  selector: 'app-form-dynamic',
  templateUrl: './form-dynamic.component.html',
  styleUrls: ['./form-dynamic.component.css']
})
export class FormDynamicComponent implements OnInit {
  dataObject = null;
  form: FormGroup;
  objectProps;
  isNew = false;

  panels = [];
  moduleName;

  newModuleName = null;

  constructor(
    private waitingService: WaitingService,
    private messageService: MessageService,
    private zone: NgZone,
    public  serverInfo: ServerInfoService,
    private http: HttpClient,
    private activatedRoute: ActivatedRoute,
    private fb: FormBuilder) {
    console.log("ConfigChangeComponent constructor");

    // this.routeTrackingService.setCurrentRoute(
    //   this.routeTrackingService.routeMap['app-version']);
    // this.form = new FormGroup({
    //   name: new FormControl()
    // })
  }

  createClearPanels(dataObject) {
    // console.log(dataObject);
    // console.log(JSON.stringify(dataObject));
    this.panels = [];
    // let keys = Object.keys(dataObject);
    // console.log(keys);
    for(let obj of dataObject) {
      // console.log(obj);
      this.formOfEmptyPanel(obj);
    }
  }

  formOfEmptyPanel(objectPanel) {
    let objects = Object.keys(objectPanel.value)
      .map(prop => {
        objectPanel.value[prop]['value'] = null;
        return Object.assign({}, { key: prop} , objectPanel.value[prop]);
      });

    let dataObject = objectPanel.value;
    const formGroup = {};
    for(let prop of Object.keys(dataObject)) {
      // console.log(prop)
      // console.log(dataObject[prop])
      formGroup[prop] = new FormControl(dataObject[prop].value || '',
        this.mapValidators(dataObject[prop].validation));
    }
    // console.log(formGroup);

    this.panels.push({ label: objectPanel['label'], value: objects, form: new FormGroup(formGroup) });
    console.log(this.panels);
    // console.log(this.panels);
  }

  createPanels(dataObject) {
    // let keys = Object.keys(dataObject);
    // console.log(keys);

    for(let obj of dataObject) {
      // console.log(obj);
      this.formOfPanel(obj);
    }
  }

  formOfPanel(objectPanel) {
    let objects = Object.keys(objectPanel.value)
      .map(prop => {
        return Object.assign({}, { key: prop} , objectPanel.value[prop]);
      });
    console.log(objectPanel);
    console.log(objects);


    let dataObject = objectPanel.value;
    const formGroup = {};
    for(let prop of Object.keys(dataObject)) {
      // console.log(prop)
      // console.log(dataObject[prop])
      formGroup[prop] = new FormControl(dataObject[prop].value || '',
        this.mapValidators(dataObject[prop].validation));
    }
    // console.log(formGroup);

    this.panels.push({ label: objectPanel['label'], value: objects, form: new FormGroup(formGroup) });
    console.log(this.panels);
    // console.log(this.panels);
  }

  ngOnInit() {
    this.isNew = false;
    console.log("ConfigChangeComponent ngOnInit");
    this.activatedRoute.queryParams.subscribe((params: Params) => {
      // console.log("ConfigChangeComponent param change");
      // console.log(params);
      this.moduleName = params['module-name'];

      if(this.moduleName) {
        let url = this.serverInfo.getServerBaseUrl() + 'switch/runtime/configuration?module-name=' + this.moduleName;
        // let url = 'http://192.168.50.134:6116/switch/runtime/configuration?moduleName=general-configuration';

        // this.http.get(url, { headers: this.userAuthService.getBasicHeader() }).subscribe(res => {
        this.http.get(url).subscribe(res => {
          // console.log(res);
          this.panels = [];
          this.dataObject = res;
          this.createPanels(res);
        }, err => {

        })
      }
    });

    // this.createPanels(this.dataObject);


    // this.createPanels(this.dataObject);


    // this.objectProps =
    //   Object.keys(this.dataObject)
    //     .map(prop => {
    //       return Object.assign({}, { key: prop} , this.dataObject[prop]);
    //     });

    // setup the form
    // const formGroup = {};
    // for(let prop of Object.keys(this.dataObject)) {
    //   formGroup[prop] = new FormControl(this.dataObject[prop].value || '',
    //     this.mapValidators(this.dataObject[prop].validation));
    // }
    //
    // this.form = new FormGroup(formGroup);
  }

  private mapValidators(validators) {
    const formValidators = [];

    if(validators) {
      for(const validation of Object.keys(validators)) {
        if(validation === 'required') {
          formValidators.push(Validators.required);
        } else if(validation === 'min') {
          formValidators.push(Validators.min(validators[validation]));
        }
      }
    }

    return formValidators;
  }

  actionClick(actionName) {
  }

  onSubmit(form) {
    // console.log(form);
  }

  createGroup() {

  }

  postConfig(panel) {
    if(!this.isNew) {
      this.waitingService.doWait();
      let url = this.serverInfo.getServerBaseUrl() + 'switch/runtime/configuration?module-name=' + this.moduleName;
      this.http.put(url, panel.form.value)
        .subscribe(res => {
          this.waitingService.endWait();
          this.messageService.add(res['message']);
        }, err => {
          this.waitingService.endWait();
          this.messageService.add("با شکست مواجه شد");
        });
    } else {
      this.waitingService.doWait();
      let url = this.serverInfo.getServerBaseUrl() + 'switch/runtime/configuration?module-name=' + this.newModuleName + '&template-module=' + this.moduleName;
      this.http.post(url, panel.form.value)
        .subscribe(res => {
          this.waitingService.endWait();
          this.messageService.add(res['message']);
        }, err => {
          this.waitingService.endWait();
          this.messageService.add("با شکست مواجه شد");
        });
    }

  }

  historyPanel = null;

  createNew() {
    this.isNew = true;
    this.historyPanel = this.panels;
    this.zone.runTask(() => {
      this.createClearPanels(this.dataObject);
    });
    console.log(this.panels);
  }

  cancelNew() {
    this.isNew = false;
    this.panels = this.historyPanel;
    this.historyPanel = null;
  }
}


export const config = {
  "default-app-server-username":{
    "label":"default-app-server-username",
    "value":"Zgiot09KU14\u003d",
    "type":"text"
  },
  "default-iso8583-version":{
    "label":"default-iso8583-version",
    "value":"87",
    "type":"select",
    "options":[
      { "label": "Iso8583-v93", "value": "93"},
      { "label": "Iso8583-v87", "value": "87"},
      { "label": "Iso8583-v2003", "value": "2003"}
    ]
  },
  "default-app-server-password":{
    "label":"default-app-server-password","value":"T0haAa0oGUDb8zfpYg97xg\u003d\u003d","type":"text"
  },
  "transaction-response-timeout":{
    "label":"transaction-response-timeout","value":"20","type":"text"
  },
  "default-in-space-timeout":{
    "label":"default-in-space-timeout","value":"15","type":"text"
  },
  "logging-level":{
    "label":"logging-level","value":"config","type":"text"
  },
  "default-app-server-url":{
    "label":"default-app-server-url","value":"t3://192.168.88.7:7001","type":"text"
  },
  "switch-iin":{
    "label":"switch-iin","value":"603770","type":"text"
  },
  "transaction-processing-timeout":{
    "label":"transaction-processing-timeout","value":"30","type":"text"
  }
}

export const config2 = {
  "default-app-server-username":{
    "label":"default-app-server-username","value":"Zgiot09KU14\u003d","type":"text"
  },
  "default-iso8583-version":{
    "label":"default-iso8583-version","value":"87","type":"select",
    "options":[
      {"label":"Iso8583-v87","value":"87"},
      {"label":"Iso8583-v93","value":"93"},
      {"label":"Iso8583-v2003","value":"2003"}
    ]
  },
  "default-app-server-password":{
    "label":"default-app-server-password","value":"T0haAa0oGUDb8zfpYg97xg\u003d\u003d","type":"text"
  },
  "transaction-response-timeout":{
    "label":"transaction-response-timeout","value":"20","type":"text"
  },
  "default-in-space-timeout":{
    "label":"default-in-space-timeout","value":"15","type":"text"
  },
  "logging-level":{
    "label":"logging-level","value":"config","type":"text"
  },
  "default-app-server-url":{
    "label":"default-app-server-url","value":"t3://192.168.88.7:7001","type":"text"
  },
  "switch-iin":{
    "label":"switch-iin","value":"603770","type":"text"
  },
  "transaction-processing-timeout":{
    "label":"transaction-processing-timeout","value":"30","type":"text"
  }
}

export const person = [{
  label: 'Name',
  value: {
    name: {
      label: 'Name',
      value: 'Juri',
      type: 'text',
      validation: {
        required: true
      }
    },
    age: {
      label: 'Age',
      value: 32,
      type: 'number',
      validation: {
        required: true
      }
    },
    gender: {
      label: 'Gender',
      value: 'M',
      type: 'radio',
      options: [
        { label: "Male", value: 'M'},
        { label: "Female", value: 'F'}
      ]
    },
    'city/ali': {
      label: 'City',
      value: '39010',
      type: 'select',
      options: [
        { label: "(choose one)", value: ''},
        { label: "Bolzano", value: '39100'},
        { label: "Meltina", value: '39010'},
        { label: "Appiano", value: '39057'}
      ],
      validation: {
        required: true
      }
    }
  }
}];

export const CURRENCY_INFO_MANAGER = [{"label":"currency-info-manager","value":{"exchange-rates-cache-behavior/init-failure-max-retries":{"label":"init-failure-max-retries","value":"*","type":"text","pattern":"text"},"currencies-cache-behavior/init-failure-retry-interval":{"label":"init-failure-retry-interval","value":"1","type":"text","pattern":"number"},"currencies-cache-behavior/init-failure-max-retries":{"label":"init-failure-max-retries","value":"*","type":"text","pattern":"text"},"currencies-cache-behavior/first-refresh-at":{"label":"first-refresh-at","value":"+1","type":"text","pattern":"number"},"default-conversion-exchange-rate":{"label":"default-conversion-exchange-rate","value":"1","type":"text","pattern":"number"},"default-commission-fee-percent":{"label":"default-commission-fee-percent","value":"0","type":"text","pattern":"number"},"default-transaction-currency-code":{"label":"default-transaction-currency-code","value":"840","type":"text","pattern":"number"},"cardholder-currency-code-lenient-mode":{"label":"cardholder-currency-code-lenient-mode","value":"yes","type":"text","pattern":"text"},"block-unmatched-terminal-currency":{"label":"block-unmatched-terminal-currency","value":"no","type":"text","pattern":"text"},"exchange-rates-cache-behavior/first-refresh-at":{"label":"first-refresh-at","value":"@h","type":"text","pattern":"text"},"exchange-rates-cache-behavior/time-unit":{"label":"time-unit","value":"minutes","type":"text","pattern":"text"},"store-provider/implementor":{"label":"implementor","value":"com.dml.sima2.dao.controller.mulitcurrency.MultiCurrencyInfoDao","type":"text","pattern":"text"},"target-currency-decimal-places-lenient-mode":{"label":"target-currency-decimal-places-lenient-mode","value":"yes","type":"text","pattern":"text"},"default-target-currency-decimal-places":{"label":"default-target-currency-decimal-places","value":"0","type":"text","pattern":"number"},"store-provider/database-instance":{"label":"database-instance","value":"basic-info","type":"text","pattern":"text"},"exchange-rates-cache-behavior/init-failure-retry-interval":{"label":"init-failure-retry-interval","value":"1","type":"text","pattern":"number"},"transaction-currency-code-lenient-mode":{"label":"transaction-currency-code-lenient-mode","value":"yes","type":"text","pattern":"text"},"transaction-currency-decimal-places-lenient-mode":{"label":"transaction-currency-decimal-places-lenient-mode","value":"yes","type":"text","pattern":"text"},"default-cardholder-currency-code":{"label":"default-cardholder-currency-code","value":"364","type":"text","pattern":"number"},"store-provider/terminal-database":{"label":"terminal-database","value":"Terminal","type":"text","pattern":"text"},"commission-fee-lenient-mode":{"label":"commission-fee-lenient-mode","value":"yes","type":"text","pattern":"text"},"conversion-exchange-rate-lenient-mode":{"label":"conversion-exchange-rate-lenient-mode","value":"no","type":"text","pattern":"text"},"default-transaction-currency-decimal-places":{"label":"default-transaction-currency-decimal-places","value":"0","type":"text","pattern":"number"},"currencies-cache-behavior/time-unit":{"label":"time-unit","value":"minutes","type":"text","pattern":"text"},"exchange-rates-cache-behavior/cache-refresh-interval":{"label":"cache-refresh-interval","value":"60","type":"text","pattern":"number"},"currencies-cache-behavior/cache-refresh-interval":{"label":"cache-refresh-interval","value":"60","type":"text","pattern":"number"},"fail-multiple-resolved-rates":{"label":"fail-multiple-resolved-rates","value":"no","type":"text","pattern":"text"},"favor-specificity-over-newer-rate":{"label":"favor-specificity-over-newer-rate","value":"yes","type":"text","pattern":"text"}}}]
