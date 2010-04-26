package micropng.pngoptimization;

import java.util.ArrayList;

import micropng.ChunkSequence;
import micropng.FourByteConverter;
import micropng.chunk.CRCCalculator;
import micropng.chunk.Chunk;
import micropng.chunk.Data;
import micropng.chunk.DataGroup;
import micropng.chunk.OrganisationSequence;
import micropng.chunk.OrganisationUnit;
import micropng.chunk.Type;

public class OptimizerChunkAggregation {

    public void optimize(OrganisationSequence organisationSequence) {
	//TODO: make it handle IDAT sizes >= 1GB correctly

	for (OrganisationUnit u : organisationSequence) {
	    if (Type.IDAT.equals(u.getType())) {
		ChunkSequence chunkSequence = u.getChunks();
		if (chunkSequence.size() > 1) {
		    //TODO: there is probably a more simple solution
		    ArrayList<Data> currentList = new ArrayList<Data>(chunkSequence.size());
		    int type = Type.IDAT.toInt();
		    DataGroup data;
		    CRCCalculator crcCalculator;
		    int crc;

		    for (Chunk c : chunkSequence) {
			currentList.add(c.getData());
		    }
		    data = new DataGroup(currentList);
		    crcCalculator = new CRCCalculator();
		    crc = crcCalculator.calculate(FourByteConverter.intArrayValue(type), data.getStream());

		    ChunkSequence seq = new ChunkSequence();
		    seq.add(new Chunk(type, data, crc));
		    
		    organisationSequence.set(organisationSequence.indexOf(u), new OrganisationUnit(seq, u.getPreviousType()));
		}
	    }
	}
    }
}
