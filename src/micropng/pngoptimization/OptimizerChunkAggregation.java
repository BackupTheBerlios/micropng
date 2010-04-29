package micropng.pngoptimization;

import java.util.ArrayList;
import java.util.Iterator;

import micropng.ChunkSequence;
import micropng.chunk.CRCCalculator;
import micropng.chunk.Chunk;
import micropng.chunk.Data;
import micropng.chunk.DataGroup;
import micropng.chunk.OrganisationSequence;
import micropng.chunk.OrganisationUnit;
import micropng.chunk.RAMData;
import micropng.chunk.Type;
import micropng.pngio.PNGProperties;

public class OptimizerChunkAggregation {

    public void optimize(OrganisationSequence organisationSequence) {
	for (OrganisationUnit u : organisationSequence) {
	    if (Type.IDAT.equals(u.getType())) {
		ChunkSequence inputSequence = u.getChunks();
		if (inputSequence.size() > 1) {
		    Iterator<Chunk> inputSequenceIterator = inputSequence.iterator();
		    Chunk nextChunk;
		    Data nextData;

		    int newLength = 0;
		    int newType = Type.IDAT.toInt();
		    DataGroup newData;
		    int newCrc;

		    ArrayList<Data> dataList = new ArrayList<Data>();
		    ChunkSequence newChunkSequence = new ChunkSequence();

		    while (inputSequenceIterator.hasNext()) {
			nextChunk = inputSequenceIterator.next();
			nextData = nextChunk.getData();

			if (nextData.getSize() == 0) {
			    continue;
			}

			if ((nextData.getSize() == PNGProperties.getMaxSize()) && (newLength == 0)) {
			    newChunkSequence.add(nextChunk);
			    continue;
			}

			if (nextData.getSize() <= PNGProperties.getMaxSize() - newLength) {
			    dataList.add(nextData);
			} else {
			    int firstPartLength = PNGProperties.getMaxSize() - newLength;
			    Data firstPart = new RAMData(nextData.getArray(0, firstPartLength));
			    Data secondPart = new RAMData(nextData.getArray(0, nextData.getSize() - firstPartLength));

			    dataList.add(firstPart);
			    newData = new DataGroup(dataList);
			    newCrc = CRCCalculator.calculate(newType, newData);
			    newChunkSequence.add(new Chunk(newType, newData, newCrc));

			    dataList = new ArrayList<Data>();
			    dataList.add(secondPart);
			}
		    }

		    newData = new DataGroup(dataList);
		    newCrc = CRCCalculator.calculate(newType, newData);
		    newChunkSequence.add(new Chunk(newType, newData, newCrc));

		    organisationSequence.set(organisationSequence.indexOf(u), new OrganisationUnit(newChunkSequence, u.getPreviousType()));
		}
	    }
	}
    }
}
