#ifdef GL_ES
   #define LOWP lowp
   precision mediump float;
#else
   #define LOWP
#endif

varying vec2 vTextureCoord;

void main(void) {
	if(vTextureCoord.x > 0.5) {
		gl_FragColor = vec4(0.0, 1.0, 0.0, 1.0);
	} else {
		gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
	}
	
	if(vTextureCoord.y > 0.9) {
		gl_FragColor = vec4(0.0, 0.0, 1.0, 1.0);
	}
}