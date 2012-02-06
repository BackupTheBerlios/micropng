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
	    if (Type.IDAT.toInt() == u.getType()) {
		final ChunkSequence inputSequence = u.getChunks();
		if (inputSequence.size() > 1) {
		    final ChunkSequence newChunkSequence = new ChunkSequence();
		    final int newType = Type.IDAT.toInt();
		    final int maxSize = PNGProperties.getMaxSize();

		    DataGroup nextNewDataGroup;
		    int nextNewDataGroupLength = 0;
		    int nextNewChunkCrc;
		    ArrayList<DataField> nextDataList = new ArrayList<DataField>();

		    for (final Chunk nextChunk : inputSequence) {
			final DataField nextInputData = nextChunk.getData();

			if (nextInputData.getSize() == 0) {
			    continue;
			}

			if (nextInputData.getSize() <= maxSize - nextNewDataGroupLength) {
			    nextDataList.add(nextInputData);
			    nextNewDataGroupLength += nextInputData.getSize();
			} else {
			    final int firstPartLength = maxSize - nextNewDataGroupLength;
			    final DataField firstPart = new RAMData(nextInputData.getArray(0,
				    firstPartLength));
			    final DataField secondPart = new RAMData(nextInputData.getArray(0,
				    nextInputData.getSize() - firstPartLength));

			    nextDataList.add(firstPart);
			    nextNewDataGroup = new DataGroup(nextDataList);
			    nextNewChunkCrc = CRCCalculator.calculate(newType, nextNewDataGroup);
			    newChunkSequence.add(new Chunk(newType, nextNewDataGroup,
				    nextNewChunkCrc));

			    nextDataList = new ArrayList<DataField>();
			    nextDataList.add(secondPart);
			    nextNewDataGroupLength = secondPart.getSize();
			}
		    }

		    nextNewDataGroup = new DataGroup(nextDataList);
		    nextNewChunkCrc = CRCCalculator.calculate(newType, nextNewDataGroup);
		    newChunkSequence.add(new Chunk(newType, nextNewDataGroup, nextNewChunkCrc));

		    organisationSequence.set(organisationSequence.indexOf(u), new OrganisationUnit(
			    newChunkSequence, u.getPreviousType()));
		}
	    }
	}
    }
}
