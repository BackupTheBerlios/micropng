package micropng.chunk;

public interface ChunkFactory<T extends Chunk> {

    public T produce(int[] type, FileData data, int crc) throws UnknownCriticalChunkException;
}
