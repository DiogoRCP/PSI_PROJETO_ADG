<?php

use yii\helpers\Html;

/* @var $this yii\web\View */
/* @var $model backend\models\Companies */

$this->title = 'Create Company';

?>
<div class="companies-create">
    <div class="companies-content">
        <h1><?= Html::encode($this->title) ?></h1>
        <?= Html::a('Back', ['index'], ['class' => 'btn btn-primary']) ?>
        <?= $this->render('_form', [
            'model' => $model,

        ]) ?>
    </div>
</div>
