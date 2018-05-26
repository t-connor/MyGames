##coding  : utf-8

import os, re

def loadfile(filename):
    fd = open(filename)
    arrayOfLine = fd.readlines()
    numberOfLine = len(arrayOfLine)
    index = 0

    for line in arrayOfLine:
        apn_temp = str(line)
        #print apn_temp
        
        p_carrier = r'carrier="(.*?)"'
        p_mcc = r'mcc="(.*?)"'
        p_mnc = r'mnc="(.*?)"'
        p_apn = r'apn="(.*?)"'
        p_user = r'user="(.*?)"'
        p_password = r'password="(.*?)"'
        p_mmsc = r'mmsc="(.*?)"'
        p_mmsproxy = r'mmsproxy="(.*?)"'
        p_mmsport = r'mmsport="(.*?)"'
        p_type = r'type="(.*?)"'
        p_authenticationtype = r'authenticationtype="(.*?)"'
        p_protocol = r'protocol="(.*?)"'
        
        carrier = re.findall(p_carrier, apn_temp)
        mcc = re.findall(p_mcc, apn_temp)
        mnc = re.findall(p_mnc, apn_temp)
        mccmnc = mcc + mnc
        apn = re.findall(p_apn, apn_temp)
        user = re.findall(p_user, apn_temp)
        password = re.findall(p_password, apn_temp)
        mmsc = re.findall(p_mmsc, apn_temp)
        mmsproxy = re.findall(p_mmsproxy, apn_temp)
        mmsport = re.findall(p_mmsport, apn_temp)
        mtype = re.findall(p_type, apn_temp)
        authenticationtype = re.findall(p_authenticationtype, apn_temp)
        protocol = re.findall(p_protocol, apn_temp)

        ##print carrier[0]
        
        print '{'
        print '\t{'
        print '\t\t0, 0,'
        print '\t\tCUSTOM_DTCNT_PROF_TYPE_FACTORY_CONF,/* type */ '
        print '\t\tCUSTOM_DTCNT_SIM_TYPE_1,	  /* SIM1/SIM2 */'
        print '\t\t(const kal_uint8*)L"%s", /* Account Name */' %(carrier[0])
        if len(mmsc) == 0:
            print '\t\t"", /* Home page */'
        else:
            print '\t\t"%s", /* Home page */'  %(mmsc[0])
        print '\t\t{'
        print '\t\t\tCUSTOM_DTCNT_PROF_GPRS_AUTH_TYPE_NORMAL,'

        if len(user) == 0 and len(password) == 0:
            print '\t\t\t"", ""  /* username, password */'
        elif len(user) == 1 and len(password) == 0:
            print '\t\t\t"%s", ""  /* username, password */'  %(user[0])            
        else:
            print '\t\t\t"%s", "%s"  /* username, password */'  %(user[0], password[0])
        print '\t\t},'

        if len(mmsc) == 0:
            print '\t\t0,'
        else:
            print '\t\t1,'
        print '\t\tCUSTOM_DTCNT_PROF_PX_SRV_HTTP,	/* proxy service type */'

        if len(mmsport) == 0:
            print '\t\t0, /* proxy port */'
        else:
            print '\t\t%s, /* proxy port */' %(mmsport[0])

        if len(mmsproxy) == 0:
            print '\t\t"0.0.0.0", /* proxy address, domain name */'
        else:
            print '\t\t"%s", /* proxy address, domain name */' %(mmsproxy[0])       
            
        print '\t\t"", "",  /* proxy username, password */'
        print '\t\t"", "", "", "", "", "",'
        
        if mtype[0] == 'mms':
            print '\t\tDTCNT_APPTYPE_MMS,'
        else:
            print '\t\tDTCNT_APPTYPE_EMAIL|DTCNT_APPTYPE_BRW_HTTP|DTCNT_APPTYPE_WIFI_TETHERING|DTCNT_APPTYPE_JAVA|DTCNT_APPTYPE_TETHERING|DTCNT_APPTYPE_USB_TETHERING|DTCNT_APPTYPE_DEF|DTCNT_APPTYPE_MRE_WAP, /* app type */'

        print '\t\t"%s" /* SIM ID */'    %(mcc[0]+mnc[0])
        print '\t},(const kal_uint8*)"%s"'  %(apn[0])
        print '},\n\n\n'
        
        index += 1


if __name__ == '__main__':
    loadfile('apnsconf.txt')
    
