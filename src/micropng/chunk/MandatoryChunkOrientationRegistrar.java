package micropng.chunk;

import micropng.pngoptimization.Optimizer;

public class MandatoryChunkOrientationRegistrar {

    public MandatoryChunkOrientationRegistrar(Optimizer optimizer) {
	new bKGD(optimizer);
	new cHRM(optimizer);
	new gAMA(optimizer);
	new hIST(optimizer);
	new iCCP(optimizer);
	new IDAT(optimizer);
	new IEND(optimizer);
	new IHDR(optimizer);
	new iTXT(optimizer);
	new pHYs(optimizer);
	new PLTE(optimizer);
	new sBIT(optimizer);
	new sPLT(optimizer);
	new sRGB(optimizer);
	new tEXt(optimizer);
	new tIME(optimizer);
	new tRNS(optimizer);
	new zTXT(optimizer);
    }
}
