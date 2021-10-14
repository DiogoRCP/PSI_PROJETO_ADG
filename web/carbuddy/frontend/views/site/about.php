<?php

/* @var $this yii\web\View */

use yii\helpers\Html;

$this->title = 'About';

?>
<div class="site-about">
    <h1><?= Html::encode($this->title) ?></h1>
    <br>
    <div class="body-content">
        <div class="row">
            <div class="col-lg-6">
                <h2>Goals</h2>

                <p>Elaboração de um site para gestão do produto por parte do gestor do aplicativo e dos mecânicos.
                    Elaboração de uma aplicação em Android para utilização do produto por parte dos clientes e mecânicos.
                    Elaboração de uma base de dados para guardar toda a informação contida tanto no site como na aplicação Android.
                    Elaboração de uma API para conectar a base de dados ao site e à aplicação Android.</p>

             </div>
            <div class="col-lg-6">
                <h2>GitHub and Jira</h2>

                <p>To manage the project, we use Jira and GitHub.</p>
                <p><a class="btn btn-outline-secondary" href="https://github.com/DiogoRCP/PSI_PROJETO_ADG">Git Repository &raquo;</a></p>

                <p><a class="btn btn-outline-secondary" href="https://psi-da-mds-ga.atlassian.net/jira/software/projects/PPA/boards/4/backlog">Jira &raquo;</a></p>
            </div>
        </div>

</div>
