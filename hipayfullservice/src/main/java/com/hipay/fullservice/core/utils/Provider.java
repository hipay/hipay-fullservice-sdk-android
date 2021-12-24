package com.hipay.fullservice.core.utils;

import android.nfc.tech.IsoDep;
import android.util.Log;

import com.github.devnied.emvnfccard.enums.SwEnum;
import com.github.devnied.emvnfccard.exception.CommunicationException;
import com.github.devnied.emvnfccard.parser.IProvider;
import com.github.devnied.emvnfccard.utils.TlvUtil;

import java.io.IOException;

import fr.devnied.bitlib.BytesUtils;

/**
 * Provider used to communicate with EMV card
 *
 * @author Millau Julien
 *
 */
public class Provider implements IProvider {

    /**
     * TAG for logger
     */
    private static final String TAG = Provider.class.getName();

    /**
     * Logger
     */
    private StringBuffer log = new StringBuffer();

    /**
     * Tag comm
     */
    private IsoDep tagCom;

    public Provider(IsoDep tagCom) {
        this.tagCom = tagCom;
    }

    @Override
    public byte[] transceive(final byte[] pCommand) throws CommunicationException {
        if (com.hipay.fullservice.BuildConfig.DEBUG) {
            Log.d(TAG, "send: " + BytesUtils.bytesToString(pCommand));
        }
        log.append("=================<br/>");
        log.append("<font color='green'><b>send:</b> " + BytesUtils.bytesToString(pCommand)).append("</font><br/>");

        byte[] response;
        try {
            // send command to emv card
            response = tagCom.transceive(pCommand);
        } catch (IOException e) {
            throw new CommunicationException(e.getMessage());
        }

        log.append("<font color='blue'><b>resp:</b> " + BytesUtils.bytesToString(response)).append("</font><br/>");
        Log.d(TAG, "resp: " + BytesUtils.bytesToString(response));
        try {
            Log.d(TAG, "resp: " + TlvUtil.prettyPrintAPDUResponse(response));
            SwEnum val = SwEnum.getSW(response);
            if (val != null) {
                Log.d(TAG, "resp: " + val.getDetail());
            }
            log.append("<pre>").append(TlvUtil.prettyPrintAPDUResponse(response).replace("\n", "<br/>").replace(" ", "&nbsp;"))
                    .append("</pre><br/>");
        } catch (Exception e) {
        }

        return response;
    }

    @Override
    public byte[] getAt() {
        return tagCom.getHistoricalBytes();
    }

    public void setTagCom(final IsoDep mTagCom) {
        this.tagCom = mTagCom;
    }

    public StringBuffer getLog() {
        return log;
    }

}
