#ifdef GL_ES
precision highp float;
#endif

uniform vec2 resolution;
uniform float time;
uniform sampler2D uTexture;

void main(void)
{
    vec2 p = -1.0 + 2.0 * gl_FragCoord.xy / resolution.xy;
    vec2 uv;

    float an = time * 0.25;

    float x = p.x * cos(an) - p.y * sin(an);
    float y = p.x * sin(an) + p.y * cos(an);
     
    uv.x = 0.25 * x / abs(y);
    uv.y = 0.20 * time + .25 / abs(y);

    gl_FragColor = vec4(texture2D(uTexture, uv).xyz * y * y, 1.0);
}
