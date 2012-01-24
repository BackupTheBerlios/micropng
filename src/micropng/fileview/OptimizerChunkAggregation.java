package micropng.fileview;

import java.util.ArrayList;

import micropng.chunkview.ChunkSequence;
import micropng.chunkview.chunk.CRCCalculator;
import micropng.chunkview.chunk.Chunk;
import micropng.chunkview.chunk.DataField;
import micropng.chunkview.chunk.DataGroup;
import micropng.chunkview.chunk.OrganisationSequence;
import micropng.chunkview.chunk.OrganisationUnit;
import micropng.chunkview.chunk.RAMData;
import micropng.chunkview.chunk.Type;
import micropng.fileview.PNGProperties;

public class OptimizerChunkAggregation {

    public void optimize(OrganisationSequence organisationSequence) {
	for (OrganisationUnit u : organisationSequence) {
	    if (Type.IDAT.equals(u.getType())) {
		ChunkSequence inputSequence = u.getChunks();
		if (inputSequence.size() > 1) {
		    int newLength = 0;
		    int newType = Type.IDAT.toInt();
		    DataGroup newData;
		    int newCrc;

		    ArrayList<DataField> dataList = new ArrayList<DataField>();
		    ChunkSequence newChunkSequence = new ChunkSequence();

		    for (Chunk nextChunk : inputSequence) {
			DataField nextData = nextChunk.getData();

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
			    DataField firstPart = new RAMData(nextData.getArray(0, firstPartLength));
			    DataField secondPart = new RAMData(nextData.getArray(0, nextData.getSize() - firstPartLength));

			    dataList.add(firstPart);
			    newData = new DataGroup(dataList);
			    newCrc = CRCCalculator.calculate(newType, newData);
			    newChunkSequence.add(new Chunk(newType, newData, newCrc));

			    dataList = new ArrayList<DataField>();
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
