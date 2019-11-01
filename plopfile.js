module.exports = function(plop) {
    plop.setActionType('echo', function(answers, config, plop) {
        console.log('You have entered: ', answers)
        return true
    });
    plop.setGenerator('code-gen', {
        prompts: [
            {
                message: 'Which template you would like to use?',
                type: 'list',
                name: 'template',
                choices: ['kotlin-jvm'],
                default: 0
            },
            {
                message: 'What is the groupId of the project?',
                type: 'input',
                name: 'groupId',
                default: 'com.github.lawkai',
                transformer: (input) => { return plop.getHelper('dotCase')(input) }
            },
            {
                message: 'What is the artifactId of the project?',
                type: 'input',
                name: 'artifactId',
                transformer: (input) => { return plop.getHelper('dashCase')(input) }
            }
        ],
        actions: (data) => {
            return [
                { type: 'echo' },
                {
                    type: 'addMany',
                    base: 'templates/{{template}}',
                    templateFiles: '**/!(README.md)',
                    globOptions: { dot: true },
                    destination: '{{artifactId}}'
                }
            ]
        }        
    })
}
