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

    float a = atan(p.y, p.x);
    float r = sqrt(dot(p, p));

    uv.x = 7.0 * a / 3.1416;
    uv.y = -time + sin(7.0 * r + time) + 0.7 * cos(time + 7.0 * a);

    float w = 0.5 + 0.5 * (sin(time + 7.0 * r) + 0.7 * cos(time + 7.0 * a));

    vec3 col = texture2D(uTexture, uv * 0.5).xyz;

    gl_FragColor = vec4(col * w, 1.0);
}
