#!/usr/bin/env node
const { spawn } = require('child_process');

const port = process.env.PORT || '3000';
const args = ['start', '-p', port];

const next = spawn('next', args, {
  stdio: 'inherit',
  shell: true
});

next.on('close', (code) => {
  process.exit(code);
});

