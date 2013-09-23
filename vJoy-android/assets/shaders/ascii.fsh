#ifdef GL_ES
   #define LOWP lowp
   precision mediump float;
#else
   #define LOWP
#endif

uniform sampler2D uTexture;
uniform vec2 resolution;
varying vec2 vTextureCoord;

float character(float n, vec2 p)
{
	p = floor(p * vec2(4.0, -4.0) + 2.5);
	if (clamp(p.x, 0.0, 4.0) == p.x && clamp(p.y, 0.0, 4.0) == p.y)
	{
		float k = p.x + p.y*5.0;
		if (int(mod(n/(exp2(k)), 2.0)) == 1) return 1.0;
	}	
	return 0.0;
}

void main()
{
	vec2 uv = gl_FragCoord.xy;
	vec3 col = texture2D(uTexture, floor(uv / 8.0) * 8.0 / resolution.xy).rgb;
	uv.y = 1.0 - uv.y;
	

	float gray = (col.r * 0.21 + col.g * 0.71 + col.b * 0.07);
	
	float n = 65536.0;              // .
	if (gray > 0.2) n = 65600.0;    // :
	if (gray > 0.3) n = 332772.0;   // *
	if (gray > 0.4) n = 15255086.0; // o 
	if (gray > 0.5) n = 23385164.0; // &
	if (gray > 0.6) n = 15252014.0; // 8
	if (gray > 0.7) n = 11512810.0; // #
	if (gray > 0.8) n = 13199452.0; // @
	
	vec2 p = mod(uv / 4.0, 2.0) - vec2(1.0);
	col = col * character(n, p);
	
	gl_FragColor = vec4(col, 1.0);
}