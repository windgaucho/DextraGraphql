package gov.dgipjn.dextra.repository.lotus;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.Callable;

import com.mindoo.domino.jna.NotesCollection;
import com.mindoo.domino.jna.NotesDatabase;
import com.mindoo.domino.jna.NotesViewEntryData;
import com.mindoo.domino.jna.constants.Navigate;
import com.mindoo.domino.jna.constants.ReadMask;
import com.mindoo.domino.jna.gc.NotesGC;
import com.mindoo.domino.jna.utils.IDUtils;

import lotus.domino.NotesFactory;
import lotus.domino.NotesThread;
import lotus.domino.Session;
import gov.dgipjn.dextra.model.Persona;
import gov.dgipjn.dextra.repository.PersonaRepository;

public class PersonaRepositoryLotusImpl implements PersonaRepository {

	@Override
	public List<Persona> getAll() {
        List<Persona> personas = new ArrayList<Persona>();

        try {
            NotesThread.sinitThread();
            Session session = NotesFactory.createSessionWithFullAccess("kaepa");

            NotesGC.runWithAutoGC(new Callable<Object>() {

                public Object call() throws Exception {
                    //optional step, but useful for some use cases:
                    //store the effective username in IDUtils for the current code block;
                    //we need this because there's no C API to read the current HTTP webuser
                    IDUtils.setEffectiveUsername(session.getEffectiveUserName());

                    //open database as the current session's effective user
                    NotesDatabase dbData = new NotesDatabase(session, "", "ofiadmjda.nsf");
                    //alternative: open database as a named user:
                    //String userName = "Karsten Lehmann/Mindoo";
                    //NotesDatabase dbData = new NotesDatabase("MyServer/MyCompany", "path/to/db.nsf", userName);
                    //or as the current ID owner (e.g. the Domino server) if userName="" or null

                    NotesCollection view = dbData.openCollectionByName("Por Ficha General");
                    // "0" means one entry above the first row, not using "1" here, because that entry could be
                    // hidden by reader fields
                    String startPos = "0";
                    // start with 1 here to move from the "0" position to the first document entry
                    int skipCount = 1;
                    // NEXT_NONCATEGORY means only return document entries; use NEXT to read categories as well
                    // or NEXT_CATEGORY to return categories only
                    EnumSet<Navigate> navigationType = EnumSet.of(Navigate.NEXT_NONCATEGORY);

                    // we want to read all view rows at once, use a lower value for web data, e.g.
                    // just return 50 or 100 entries per request and use a paging grid in the UI
                    int count = Integer.MAX_VALUE;
                    // since we want to read all view rows, fill the read buffer with all we can get (max 64K)
                    int preloadEntryCount = Integer.MAX_VALUE;

                    // decide which data to read; use SUMMARYVALUES to read column values;
                    // use SUMMARYVALUES instead of SUMMARY to get more data into the buffer.
                    // SUMMARY would not just return the column values but also the programmatic
                    // column names, eating unnecessary buffer space
                    EnumSet<ReadMask> readMask = EnumSet.of(ReadMask.NOTEUNID, ReadMask.SUMMARYVALUES);

                    // if you are a more advanced user, take a look at the code behind the last
                    // parameter; you don't have to work with NotesViewEntryData here, but
                    // can produce your own application objects directly.
                    List<NotesViewEntryData> viewEntries = view.getAllEntries(startPos,
                            skipCount, navigationType, preloadEntryCount, readMask,
                            new NotesCollection.EntriesAsListCallback(count));

                    System.out.println("Read "+viewEntries.size()+" entries");

                    for (NotesViewEntryData currEntry : viewEntries) {
                        currEntry.setPreferNotesTimeDates(true);
                        Persona p = new Persona();
                        p.setId(currEntry.getUNID());
                        p.setNombre((String) currEntry.get("Persona"));
                        personas.add(p);
                        System.out.println(currEntry.getUNID() + "\t" + currEntry.getColumnDataAsMap());
                    }

                    return null;
                }
            });

            NotesThread.stermThread();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return personas;

	}

	@Override
	public Persona getById() {
		// TODO Auto-generated method stub
		return null;
	}

}
