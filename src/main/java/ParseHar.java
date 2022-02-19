
import de.sstoehr.harreader.HarReader;
import de.sstoehr.harreader.HarReaderException;
import de.sstoehr.harreader.model.Har;
import de.sstoehr.harreader.model.HarEntry;
import de.sstoehr.harreader.model.HarHeader;
import de.sstoehr.harreader.model.HarRequest;
import de.sstoehr.harreader.model.HarResponse;

import java.io.File;
import java.util.List;

public class ParseHar
{
    public static void main (String[] args) throws HarReaderException
    {
        HarReader harReader = new HarReader ();
        Har harFile = harReader.readFromFile(new File(args[0]));
        System.out.println("Parsing HAR file created by: " + harFile.getLog().getCreator().getName());
        List<HarEntry> allEntries = harFile.getLog ().getEntries ();
        for (HarEntry currEntry: allEntries)
        {
            System.out.println ("REQUEST:");
            // Dump Request
            HarRequest harRequest = currEntry.getRequest ();
            System.out.println (harRequest.getMethod () + " " + harRequest.getUrl ()+ " " + harRequest.getHttpVersion ());
            List <HarHeader> requestHeaders = harRequest.getHeaders ();
            for (HarHeader harHeader: requestHeaders)
                System.out.println (harHeader.getName () + ": " + harHeader.getValue ());
            String postData = harRequest.getPostData ().getText ();
            System.out.println ("");
            if (postData != null)
                System.out.println (postData);

            System.out.println ("");
            // Dump Response
            System.out.println ("RESPONSE:");
            HarResponse harResponse = currEntry.getResponse ();
            System.out.println (harResponse.getHttpVersion () + " " + harResponse.getStatus () + " " + harResponse.getStatusText ());

            List <HarHeader> responseHeaders = harResponse.getHeaders ();
            for (HarHeader harHeader: responseHeaders)
                System.out.println (harHeader.getName () + ": " + harHeader.getValue ());

            System.out.println ("");
            String bodyData = harResponse.getContent ().getText ();
            if (bodyData != null)
                System.out.println (bodyData);
            System.out.println ("");
        }
    }
}
