<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model backend\models\Companies */

$this->title = 'Create Company';

?>
<div class="companies-create">
    <div class="companies-content">
        <img src="../images/logo_white.png" width="80">
        <h1><?= Html::encode($this->title) ?></h1>
        <p>
            <?= Html::a('Back', ['index'], ['class' => 'btn btn-dark']) ?>
        </p>

        <?= $this->render('_form', [
            'model' => $model,

        ]) ?>
    </div>
</div>
